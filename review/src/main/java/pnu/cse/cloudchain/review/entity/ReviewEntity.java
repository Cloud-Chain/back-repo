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
    private String buyerid;

    @Column(nullable = false)
    private String sellerid;

    @Column(nullable = false, unique = true)
    private String carNumber;

    @Column(nullable = false)
    private String reviewRating;

    @Column(nullable = true)
    private String reviewStr;


    public static ReviewEntity createReview(ReviewDto dto) {

        return ReviewEntity.builder()
                .buyerid(dto.getBuyerid())
                .sellerid(dto.getSellerid())
                .carNumber(dto.getCarNumber())
                .reviewRating(dto.getReviewRating())
                .reviewStr(dto.getReviewStr())
                .build();
    }
}
