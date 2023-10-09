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
//    private String uploadDate;
    private String residentRegistrationNumber;
    private String phoneNumber;
    private String address;
    private String transactionState;
    private String vehicleIdentificationNumber;
}
