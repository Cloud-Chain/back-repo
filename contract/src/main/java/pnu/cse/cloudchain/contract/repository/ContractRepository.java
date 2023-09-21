package pnu.cse.cloudchain.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.entity.ContractEntity;

import java.util.List;

public interface ContractRepository extends JpaRepository<ContractEntity, String> {
    ContractEntity findBySelleridAndBuyeridAndCarNumber(String sellerid, String buyerid, String carNumber);
    List<ContractDto> findBySelleridAndCarNumber(String sellerid, String carNumber);
    List<ContractDto> findByBuyeridAndCarNumber(String buyerid, String carNumber);
    List<ContractDto> findByCarNumber(String carNumber);
    List<ContractDto> findBySellerid(String sellerid);
    List<ContractDto> findByBuyerid(String buyerid);
}
