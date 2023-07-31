package com.ssafy.crit.users.service;

import com.ssafy.crit.common.exception.BadRequestException;
import com.ssafy.crit.auth.jwt.JwtProvider;
import com.ssafy.crit.auth.dto.*;
import com.ssafy.crit.users.dto.UpdateProfilePictureDto;
import com.ssafy.crit.users.entity.Grade;
import com.ssafy.crit.users.entity.User;
import com.ssafy.crit.users.repository.UserRepository;
import com.ssafy.crit.auth.entity.enumType.AuthProvider;
import com.ssafy.crit.users.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String signUp(SignUpRequestDto signUpRequestDto) throws Exception {
        if (userRepository.findById(signUpRequestDto.getId()).isPresent()) {
            throw new BadRequestException("이미 존재하는 아이디입니다.");
        }
        User user = User.builder()
                .id(signUpRequestDto.getId())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .nickname(signUpRequestDto.getNickname())
                .role(Role.USER)
                .grade(Grade.Begginer)
                .exp(0)
                .point(0)
                .authProvider(AuthProvider.EMPTY)
                .build();

        user.passwordEncode(bCryptPasswordEncoder);
        return userRepository.save(user).getId();
    }

    public LogInResponseDto logIn(LogInRequestDto logInRequestDto) throws Exception{
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!userRepository.existsById(logInRequestDto.getId())) {
            throw new BadRequestException("존재하지 않는 아이디입니다.");
        }
        User user = userRepository.findById(logInRequestDto.getId()).get();
        if (!bCryptPasswordEncoder.matches(logInRequestDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("존재하지 않는 비밀번호입니다.");
        }

        /**
         * 이 밑에 하나의 로직은 로그인을 하루 처음 했는지에 대한 것
         */

        if(!user.getIsChecked()) {
            int exp = user.getExp();
            user.setExp(exp, false);
        }

        // 토큰 발급
        TokenDto accessTokenDto = jwtProvider.createAccessToken(logInRequestDto.getId(), user.getAuthProvider());
        TokenDto refreshTokenDto = jwtProvider.createRefreshToken(logInRequestDto.getId(), user.getAuthProvider());

        user.updateRefreshToken(refreshTokenDto.getToken(), refreshTokenDto.getTokenExpirationTime());

        return LogInResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .accessToken(accessTokenDto.getToken())
                .refreshToken(refreshTokenDto.getToken())
                .refreshTokenExpirationTime(refreshTokenDto.getTokenExpirationTime())
                .exp(user.getExp())
                .isChecked(user.getIsChecked())
                .build();
    }

    /*
    ** 로그아웃 -> DB에 저장된 리프레쉬 토큰 최신화
     */
    public void logOut(LogOutRequestDto logOutRequestDto) {
        User user = userRepository.findByRefreshToken(logOutRequestDto.getRefreshToken()).get();
        user.expireRefreshToken(new Date());
    }

    /**
     *
     */
    public String updateProfilePicture ( MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources";
        /*식별자 . 랜덤으로 이름 만들어줌*/
        UUID uuid = UUID.randomUUID();
        /*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*/
        String fileName = uuid + "_" + file.getOriginalFilename();
        /*빈 껍데기 생성*/
        /*File을 생성할건데, 이름은 "name" 으로할거고, projectPath 라는 경로에 담긴다는 뜻*/
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        return "성공";
    }

    @Transactional(readOnly = true)
    public TokenDto getAccessToken(String refreshToken) {
        String userId = (String) jwtProvider.get(refreshToken).get("userId");
        String provider = (String) jwtProvider.get(refreshToken).get("provider");
        System.out.println("in getAccessToken " + userId + "  " + provider);

        if(!userRepository.existsByIdAndAuthProvider(userId, AuthProvider.findByCode(provider.toLowerCase()))){
            throw new BadRequestException("CANNOT_FOUND_USER");
        } else if (jwtProvider.isExpiration(refreshToken)) {
            throw new BadRequestException("TOKEN_EXPIRED");
        }

        return jwtProvider.createAccessToken(userId, AuthProvider.findByCode(provider));
    }
}
