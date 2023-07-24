package self.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.study.entity.Message;
import self.study.entity.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);
}
