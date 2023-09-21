package pnu.cse.cloudchain.contract.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDto {
    private String buyerid;
    private String sellerid;

    private String carNumber;

    private String contractDate;

    private String contractAmount;

    private String contractLocation;

    private String contractState;

    private String contractPdate;
}
