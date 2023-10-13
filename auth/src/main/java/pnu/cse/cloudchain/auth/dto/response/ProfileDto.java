package pnu.cse.cloudchain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {
    private String userid;

    private String email;

    private String org;

    private String name;
    private String phoneNumber;

    private String detail;

    private String businessRegistration;
    private String profileImage;

    private String reportHistory;
}
