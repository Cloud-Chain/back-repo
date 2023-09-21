package pnu.cse.cloudchain.auth.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestProfileDto {
    private String type;
    private String name;
    private String detail;
}
