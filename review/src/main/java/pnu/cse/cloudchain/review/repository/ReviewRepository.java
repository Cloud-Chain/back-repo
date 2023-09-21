package pnu.cse.cloudchain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.cse.cloudchain.review.dto.ReviewDto;
import pnu.cse.cloudchain.review.entity.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewDto> findBySelleridAndBuyeridAndCarNumber(String sellerid, String buyerid, String carNumber);
    List<ReviewDto> findBySelleridAndCarNumber(String sellerid, String carNumber);
    List<ReviewDto> findByBuyeridAndCarNumber(String buyerid, String carNumber);
    List<ReviewDto> findByCarNumber(String carNumber);
    List<ReviewDto> findByBuyerid(String buyerid);
    List<ReviewDto> findBySellerid(String sellerid);
}
