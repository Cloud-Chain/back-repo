package pnu.cse.cloudchain.carinfo.dto;

import lombok.*;
import pnu.cse.cloudchain.carinfo.dto.request.ImagesDto;
import pnu.cse.cloudchain.carinfo.dto.request.VehicleBasicInfoDto;
import pnu.cse.cloudchain.carinfo.dto.request.VehicleDetailInfoDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspectDto {
    private Integer id;
    private Boolean inspectionStatus;
    private String requestDate;
    private String inspectionDate;
    private VehicleBasicInfoDto vehicleBasicInfo;
    private VehicleDetailInfoDto vehicleDetailInfo;
    private ImagesDto images;
    private String etc;
}
