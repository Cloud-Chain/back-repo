package pnu.cse.cloudchain.contract.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractResponseDto {
    private Integer id;
    private String model;
    private Integer mileage;
    private Integer price;
    private String seller;
    private String period;
}
