package pnu.cse.cloudchain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.cse.cloudchain.review.entity.ContractEntity;

public interface ContractRepository extends JpaRepository<ContractEntity, String> {
    ContractEntity findBySellerNameAndBuyerNameAndCarNumber(String sellerName, String buyerName, String carNumber);
    ContractEntity findBySellerNameAndCarNumber(String sellerName, String carNumber);
    ContractEntity findByBuyerNameAndCarNumber(String buyerName, String carNumber);
}
