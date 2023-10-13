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
    private String residentRegistrationNumber;
    private String phoneNumber;
    private String address;
    private String transactionState;
    private String vehicleIdentificationNumber;

    public String toString() {
        String ret = "\nContractResponse Data"
                +"\n - Id - "+id.toString()
                +"\n - UploadDate - "+period
                +"\n - Model - "+model
                +"\n - Mileage - "+mileage.toString()
                +"\n - Price - "+price.toString()
                +"\n - VehicleIdentificationNumber - "+vehicleIdentificationNumber
                +"\n - TransactionState - "+transactionState
                +"\n - Seller - "+seller
                +"\n - ResidentRegistrationNumber - "+residentRegistrationNumber
                +"\n - PhoneNumber - "+phoneNumber
                +"\n - Address - "+address;
        return ret;
    }
}
