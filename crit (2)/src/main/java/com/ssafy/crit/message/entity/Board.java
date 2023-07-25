package com.ssafy.crit.message.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.ssafy.crit.auth.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private String filename; // 파일 이름

    private String filepath; // 파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;


}
