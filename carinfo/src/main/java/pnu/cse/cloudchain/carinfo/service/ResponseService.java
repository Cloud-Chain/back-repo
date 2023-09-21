package pnu.cse.cloudchain.carinfo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.SuccessCodeDto;

import java.util.List;

@Service
public class ResponseService {
    public ResponseDto<SuccessCodeDto> successResponse(ResponseDto<SuccessCodeDto> dto) {

        return dto;
    }
    public static ResponseDto<List<CarInfoDto>> successDataResponse(ResponseDto<List<CarInfoDto>> dto){

        return dto;
    }
}
