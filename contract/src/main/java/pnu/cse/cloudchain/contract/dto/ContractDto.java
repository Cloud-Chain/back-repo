package pnu.cse.cloudchain.contract.dto;

import lombok.*;
import pnu.cse.cloudchain.contract.dto.request.AssignDto;
import pnu.cse.cloudchain.contract.dto.request.TransactionDetailsDto;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDto {
    private Integer id;

    private String uploadDate;

    private AssignDto assignor;

    private AssignDto assignee;

    private TransactionDetailsDto transactionDetails;

    @Override
    public String toString() {
        String ret = "\nContract Data "
                    +"\n - Id - "+id.toString()
                    +"\n - UploadDate - "+uploadDate
                    +"\n - Assignor - "+assignor.getString()
                    +"\n - Assignee - "+assignee.getString()
                    +"\n - TransactionDetails - "+transactionDetails.getString();
        return ret;
    }
}
