package pnu.cse.cloudchain.contract.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetailsDto {
    private String transactionState;
    private String vehicleRegistrationNumber;
    private String newVehicleRegistrationNumber;
    private String vehicleModelName;
    private String vehicleIdentificationNumber;
    private String transactionDate;
    private Integer transactionAmount;
    private String balancePaymentDate;
    private String vehicleDeliveryDate;
    private String vehicleDeliveryAddress;
    private Integer mileage;
}

