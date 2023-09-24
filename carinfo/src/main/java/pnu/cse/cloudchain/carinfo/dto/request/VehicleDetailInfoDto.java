package pnu.cse.cloudchain.carinfo.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDetailInfoDto {
    private String tuning;
    private String outerPlate;
    private String vehicleFrame;
    private String motor;
    private String transmission;
    private String steering;
    private String braking;
    private String electricity;
    private String fuel;
    private String exterior;
    private String interior;
    private String gloss;
    private String wheel;
    private String tire;
    private String glass;
}
