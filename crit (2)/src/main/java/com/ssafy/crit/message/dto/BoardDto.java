package com.ssafy.crit.message.dto;

import com.ssafy.crit.message.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
//    private String filename;
//    private String filepath;

    public static BoardDto toDto(Board board) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
//                board.getFilename(),
//                board.getFilepath(),
                board.getUser().getNickname());
    }

}