package self.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.study.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByBoardId(int boardId);
}
