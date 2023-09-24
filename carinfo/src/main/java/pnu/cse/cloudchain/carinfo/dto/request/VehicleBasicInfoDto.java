package pnu.cse.cloudchain.carinfo.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBasicInfoDto {
    private String vehicleIdentificationNumber;
    private String vehicleModelName;
    private String vehicleRegistrationNumber;
    private String gearboxType;
    private String fuelUsed;
    private Integer mileage;
    private String color;
    private String options;
}
