package pnu.cse.cloudchain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.cse.cloudchain.review.dto.ReviewDto;
import pnu.cse.cloudchain.review.entity.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findBySellerNameAndBuyerNameAndCarNumber(String sellerName, String buyerName, String carNumber);
    List<ReviewDto> findBysellerNameAndCarNumber(String sellerName, String carNumber);
    List<ReviewDto> findByBuyerNameAndCarNumber(String buyerName, String carNumber);
    List<ReviewDto> findByCarNumber(String carNumber);
    List<ReviewDto> findByBuyerName(String buyerName);
    List<ReviewEntity> findBysellerName(String sellerName);
}
