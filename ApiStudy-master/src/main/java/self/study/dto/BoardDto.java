package self.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import self.study.entity.Board;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int id;
    private String title;
    private String content;
    private String writer;

    public static BoardDto toDto(Board board) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getName());
    }

}
