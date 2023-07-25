package com.ssafy.crit.auth.entity;

import com.ssafy.crit.auth.entity.enumType.AuthProvider;
import com.ssafy.crit.auth.entity.enumType.Role;
import com.ssafy.crit.message.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(length = 300)
    private String refreshToken;

    private Date tokenExpirationTime;

    @OneToMany(mappedBy = "user")
    private List<Board> board;


    /*
    ** 엔티티 관련 비즈니스 로직
     */
    public void passwordEncode(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String refreshToken, Date refreshTokenExpirationTime) {
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = refreshTokenExpirationTime;
    }

    public void expireRefreshToken(Date now) {
        this.tokenExpirationTime = now;
    }

    public User update(String name, String picture){
        this.nickname = name;
        this.profileImageUrl = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
