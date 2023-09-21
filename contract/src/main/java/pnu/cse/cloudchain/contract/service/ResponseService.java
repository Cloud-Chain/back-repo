package pnu.cse.cloudchain.contract.service;

import org.springframework.stereotype.Service;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseDto;
import pnu.cse.cloudchain.contract.dto.response.SuccessCodeDto;

import java.util.List;

@Service
public class ResponseService {
    public ResponseDto<SuccessCodeDto> successResponse(ResponseDto<SuccessCodeDto> dto) {

        return dto;
    }
    public static ResponseDto<List<ContractDto>> successDataResponse(ResponseDto<List<ContractDto>> dto){

        return dto;
    }
}
