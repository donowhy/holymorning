package project.holymorning.login.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {

    //jwt에 대한 인증 타입 -> 여기서 Bearer 사용 (*이후 HTTP 헤더에 prefix로 붙여주는 타입)
    private String grantType;

    private String accessToken;
    private String refreshToken;

}
