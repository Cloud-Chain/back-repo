package pnu.cse.cloudchain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.cse.cloudchain.review.entity.ContractEntity;

public interface ContractRepository extends JpaRepository<ContractEntity, String> {
    ContractEntity findBySelleridAndBuyeridAndCarNumber(String sellerid, String buyerid, String carNumber);
    ContractEntity findBySelleridAndCarNumber(String sellerid, String carNumber);
    ContractEntity findByBuyeridAndCarNumber(String buyerid, String carNumber);
}
