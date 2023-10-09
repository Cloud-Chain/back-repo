package pnu.cse.cloudchain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pnu.cse.cloudchain.review.dto.ReviewDto;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(nullable = false)
    private Integer contractId;

    @Column(nullable = false)
    private String buyerName;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false, unique = true)
    private String carNumber;

    @Column(nullable = false)
    private String reviewRating;

    @Column(nullable = true)
    private String reviewStr;


    public static ReviewEntity createReview(ReviewDto dto) {

        return ReviewEntity.builder()
                .contractId(dto.getContractId())
                .buyerName(dto.getBuyerName())
                .sellerName(dto.getSellerName())
                .carNumber(dto.getCarNumber())
                .reviewRating(dto.getReviewRating())
                .reviewStr(dto.getReviewStr())
                .build();
    }
}
