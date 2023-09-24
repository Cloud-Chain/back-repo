package pnu.cse.cloudchain.contract.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pnu.cse.cloudchain.contract.dto.ContractDto;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(nullable = false)
    private String buyerid;

    @Column(nullable = false)
    private String sellerid;

    @Column(nullable = false)
    private String carNumber;

    @Column(nullable = true)
    private String contractDate;

    @Column(nullable = true)
    private String contractAmount;

    @Column(nullable = true)
    private String contractLocation;

    @Column(nullable = true)
    private String contractState;

    @Column(nullable = true)
    private String contractPdate;

//    public static ContractEntity createContract(ContractDto dto) {
//
//        return ContractEntity.builder()
//                .buyerid(dto.getBuyerid())
//                .sellerid(dto.getSellerid())
//                .carNumber(dto.getCarNumber())
//                .contractDate(dto.getContractDate())
//                .contractAmount(dto.getContractAmount())
//                .contractLocation(dto.getContractLocation())
//                .contractState(dto.getContractState())
//                .contractPdate(dto.getContractPdate())
//                .build();
//    }
    public void editContractDate(String contractDate) {
        this.contractDate = contractDate;
    }
    public void editContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }
    public void editContractLocation(String contractLocation) {
        this.contractLocation = contractLocation;
    }
    public void editContractState(String contractState) {
        this.contractState = contractState;
    }
    public void editContractPdate(String contractPdate) {
        this.contractPdate = contractPdate;
    }
}
