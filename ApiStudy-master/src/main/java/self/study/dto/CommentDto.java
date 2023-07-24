package self.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import self.study.entity.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String content;
    private String writer;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName()
        );
    }
}
