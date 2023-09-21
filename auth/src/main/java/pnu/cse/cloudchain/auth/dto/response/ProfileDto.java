package pnu.cse.cloudchain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {
    private String name;
    private String type;
    private String userid;
    private String detail;
}
