package pnu.cse.cloudchain.auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pnu.cse.cloudchain.auth.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseDataDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseProfileDto;

@Service
public class ResponseService {
    public ResponseCodeDto successResponse(ResponseCodeDto dto) {

        return dto;
    }

    public ResponseEntity<ResponseDataDto> successDataResponse(ResponseEntity<ResponseDataDto> dto) {

        return dto;
    }

    public ResponseProfileDto successProfileResponse(ResponseProfileDto dto) {

        return dto;
    }
}
