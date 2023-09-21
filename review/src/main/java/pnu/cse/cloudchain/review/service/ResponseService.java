package pnu.cse.cloudchain.review.service;

import org.springframework.stereotype.Service;
import pnu.cse.cloudchain.review.dto.ReviewDto;
import pnu.cse.cloudchain.review.dto.response.Response;
import pnu.cse.cloudchain.review.dto.response.SuccessCodeDto;

import java.util.List;

@Service
public class ResponseService {
    public Response<SuccessCodeDto> successResponse(Response<SuccessCodeDto> dto) {

        return dto;
    }
    public static Response<List<ReviewDto>> successDataResponse(Response<List<ReviewDto>> dto){

        return dto;
    }
}
