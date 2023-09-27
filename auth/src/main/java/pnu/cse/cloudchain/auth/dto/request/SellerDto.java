package pnu.cse.cloudchain.auth.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile businessRegistrationRequest;
}
