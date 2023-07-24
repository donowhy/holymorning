package project.holymorning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.holymorning.entity.member.Member;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MemberSaveDto {

    private String memberId;
    private String password;
    private String checkPassword;

    private String email;

    private String phoneNumber;
    private List<String> roles;

    public Member toEntity(){
        return new Member(memberId, password, checkPassword, email, phoneNumber, roles);
    }

}
