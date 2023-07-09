package project.holymorning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.holymorning.entity.member.Member;

@Getter
@AllArgsConstructor
@Builder
public class MemberSaveDto {

    private String memberId;
    private String password;
    private String checkPassword;

    private String email;

    private String phoneNumber;

    private final String USER;

    public Member toEntity(){
        return new Member(memberId, password, checkPassword, email, phoneNumber, USER);
    }

}
