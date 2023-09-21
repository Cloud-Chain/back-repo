package pnu.cse.cloudchain.review.dto;

import lombok.*;

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
}
