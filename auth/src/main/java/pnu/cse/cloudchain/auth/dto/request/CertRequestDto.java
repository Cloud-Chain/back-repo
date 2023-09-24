package pnu.cse.cloudchain.auth.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertRequestDto {
    private String org;
    private String userID;
    private String password;
}
