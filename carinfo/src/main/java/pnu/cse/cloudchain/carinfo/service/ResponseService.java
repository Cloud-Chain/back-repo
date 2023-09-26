package pnu.cse.cloudchain.carinfo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.InspectDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.SuccessCodeDto;

import java.util.List;

@Service
public class ResponseService {
    public ResponseCodeDto successResponse(ResponseCodeDto dto) {

        return dto;
    }
    public ResponseDto<SuccessCodeDto> successResponse(ResponseDto<SuccessCodeDto> dto) {

        return dto;
    }
    public static ResponseDto<InspectDto> successDataResponse(ResponseDto<InspectDto> dto){

        return dto;
    }
    public static ResponseDto<List<InspectDto>> successDatasResponse(ResponseDto<List<InspectDto>> dto){

        return dto;
    }
}
