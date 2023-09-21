package pnu.cse.cloudchain.auth.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyerDto {
    private String userid;
    private String password;
    private String name;
    private String detail;
}
