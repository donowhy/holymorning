package self.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.study.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
