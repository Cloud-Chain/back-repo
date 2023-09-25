package pnu.cse.cloudchain.auth.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignRequestDto {
    private String id;
    private String email;
    private String password;
}
