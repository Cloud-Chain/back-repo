package pnu.cse.cloudchain.carinfo.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagesRequestDto {
    private MultipartFile inside;
    private MultipartFile outside;
    private MultipartFile front;
    private MultipartFile left;
    private MultipartFile right;
    private MultipartFile back;
}
