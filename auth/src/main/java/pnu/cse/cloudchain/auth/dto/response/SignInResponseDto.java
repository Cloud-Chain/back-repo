package pnu.cse.cloudchain.auth.dto.response;

import lombok.*;

import javax.servlet.http.Cookie;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponseDto {
    private String id;

    private String name;

    private String org;

    private String accessToken;

}
