package pnu.cse.cloudchain.review.dto;

import lombok.*;
import pnu.cse.cloudchain.review.entity.ReviewEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Integer contractId;
    private String buyerName;
    private String sellerName;
    private String carNumber;
    private String reviewRating;
    private String reviewStr;

    public static ReviewDto createReview(ReviewEntity dto) {

        return ReviewDto.builder()
                .contractId(dto.getContractId())
                .buyerName(dto.getBuyerName())
                .sellerName(dto.getSellerName())
                .carNumber(dto.getCarNumber())
                .reviewRating(dto.getReviewRating())
                .reviewStr(dto.getReviewStr())
                .build();
    }
}
