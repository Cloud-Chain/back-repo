package pnu.cse.cloudchain.auth.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerDto {
    private String userid;
    private String email;
    private String password;
    private String name;
    private String detail;
    private String businessRegistration;
}
