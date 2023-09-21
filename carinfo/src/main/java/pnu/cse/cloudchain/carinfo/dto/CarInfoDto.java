package pnu.cse.cloudchain.carinfo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarInfoDto {
    private String model;

    private String period;

    private String carNumber;

    private Integer mileage;

    private Integer price;

    private String seller;

    private String contractState;

    private Boolean inspectState;
}
