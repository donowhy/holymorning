package self.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import self.study.entity.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private int id;
    private String title;
    private String content;
    private String senderName;
    private String receiverName;

    public static MessageDto toDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getContent(),
                message.getSender().getName(),
                message.getReceiver().getName()
        );
    }
}
