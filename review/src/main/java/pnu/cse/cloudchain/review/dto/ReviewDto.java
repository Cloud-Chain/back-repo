package pnu.cse.cloudchain.review.dto;

import lombok.*;
import pnu.cse.cloudchain.review.entity.ReviewEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private String buyerid;
    private String sellerid;
    private String carNumber;
    private String reviewRating;
    private String reviewStr;

    public static ReviewDto createReview(ReviewEntity dto) {

        return ReviewDto.builder()
                .buyerid(dto.getBuyerid())
                .sellerid(dto.getSellerid())
                .carNumber(dto.getCarNumber())
                .reviewRating(dto.getReviewRating())
                .reviewStr(dto.getReviewStr())
                .build();
    }
}
