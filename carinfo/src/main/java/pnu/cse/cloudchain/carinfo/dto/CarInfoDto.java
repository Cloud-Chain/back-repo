package pnu.cse.cloudchain.carinfo.dto;

import lombok.*;
import pnu.cse.cloudchain.carinfo.dto.request.AssignDto;
import pnu.cse.cloudchain.carinfo.dto.request.TransactionDetailsDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarInfoDto {
    private Integer id;

    private String uploadDate;

    private AssignDto assignor;

    private AssignDto assignee;

    private TransactionDetailsDto transactionDetails;
}
