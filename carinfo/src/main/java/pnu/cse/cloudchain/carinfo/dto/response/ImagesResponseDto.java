package pnu.cse.cloudchain.carinfo.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagesResponseDto {
    private String inside;
    private String outside;
    private String front;
    private String left;
    private String right;
    private String back;
}
