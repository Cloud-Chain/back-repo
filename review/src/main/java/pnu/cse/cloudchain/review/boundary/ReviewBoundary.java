package pnu.cse.cloudchain.review.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.review.dto.ReviewDto;
import pnu.cse.cloudchain.review.dto.response.Response;
import pnu.cse.cloudchain.review.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.review.service.ResponseService;
import pnu.cse.cloudchain.review.control.ReviewControl;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/review")
public class ReviewBoundary {
    private final ReviewControl reviewControl;
    private final ResponseService responseService;

    @PostMapping("/review-seller")
    public Response<SuccessCodeDto> review(@RequestBody @Valid ReviewDto dto) {
        log.info("Buyer Sign-up api received RequestBody = {}", dto.toString());
        return responseService.successResponse(reviewControl.review(dto));
    }

    @GetMapping("/get-review")
    public Response<List<ReviewDto>> getReview(@RequestParam("name") @Valid String sellerName) {
        return responseService.successDataResponse(reviewControl.getReview(sellerName));
    }
}
