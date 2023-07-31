package com.ssafy.crit.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class LogInResponseDto {
    private String id;
    private String nickname;
    private String email;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpirationTime;
    private Boolean isChecked;
    private int exp;

//    @Builder
//    public LogInResponseDto(String id, String nickname, String email, String accessToken, String refreshToken, Date refreshTokenExpirationTime) {
//        this.id = id;
//        this.nickname = nickname;
//        this.email = email;
//        this.accessToken = accessToken;
//        this.refreshToken = refreshToken;
//        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
//    }


    @Builder
    public LogInResponseDto(String id, String nickname, String email, String accessToken, String refreshToken, Date refreshTokenExpirationTime, Boolean isChecked, int exp) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
        this.isChecked = isChecked;
        this.exp = exp;
    }
}
