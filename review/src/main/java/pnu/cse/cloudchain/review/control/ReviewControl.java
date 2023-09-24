package pnu.cse.cloudchain.review.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.cse.cloudchain.review.dto.ReviewDto;
import pnu.cse.cloudchain.review.dto.response.Response;
import pnu.cse.cloudchain.review.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.review.exception.CustomException;
import pnu.cse.cloudchain.review.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.review.entity.ContractEntity;
import pnu.cse.cloudchain.review.entity.ReviewEntity;
import pnu.cse.cloudchain.review.repository.ContractRepository;
import pnu.cse.cloudchain.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewControl {
    private final ContractRepository contractRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Response<SuccessCodeDto> review(ReviewDto dto) {
        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}", dto.getBuyerid(), dto.getSellerid(), dto.getCarNumber());
        ContractEntity existContractEntity = contractRepository.findBySelleridAndBuyeridAndCarNumber(dto.getSellerid(), dto.getBuyerid(), dto.getCarNumber());

        if (existContractEntity != null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
        }
        log.info("Valid contract");

        SuccessCodeDto successCode = new SuccessCodeDto();

        ReviewEntity reviewEntity = ReviewEntity.createReview(dto);
        reviewRepository.save(reviewEntity);
        log.info("Save Review in DB Successfully");
        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("리뷰 등록에 성공하였습니다.");

        return Response.success("Create Review Successfully", successCode);
    }

    @Transactional
    public Response<List<ReviewDto>> getReview(ReviewDto dto) {
        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}", dto.getBuyerid(), dto.getSellerid(), dto.getCarNumber());
        List<ReviewEntity> exist = reviewRepository.findBySelleridAndBuyeridAndCarNumber(dto.getSellerid(), dto.getBuyerid(), dto.getCarNumber());

        if (exist == null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
        }
        log.info("Valid contract");
        List<ReviewDto> dtos = new ArrayList<>();
        for (ReviewEntity re: exist) {
            ReviewDto reDto = ReviewDto.createReview(re);
            dtos.add(reDto);
        }

        return Response.success("Get Review Successfully", dtos);
    }
}
