package pnu.cse.cloudchain.carinfo.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.InspectDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.carinfo.entity.CarInfoEntity;
import pnu.cse.cloudchain.carinfo.exception.CustomException;
import pnu.cse.cloudchain.carinfo.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.carinfo.repository.CarInfoRepository;
import pnu.cse.cloudchain.carinfo.entity.CarInfoFeignEntity;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarInfoControl {
    private final CarInfoRepository carInfoRepository;
    private final CarInfoFeignEntity carInfoFeignEntity;

    @Transactional
    public ResponseDto<SuccessCodeDto> regCar(CarInfoDto dto, String userid, String causer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("org","seller");
        httpHeaders.set("userID",userid);
        httpHeaders.set("CA-User",causer);
//        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}");
        carInfoFeignEntity.regCar(dto, httpHeaders );
        log.info("Success registry carInfo");

        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("차량 등록에 성공하였습니다.");

        return ResponseDto.success("Successful registration of car info", successCode);
    }

    @Transactional
    public ResponseDto<SuccessCodeDto> regInspec(InspectDto dto) {
//        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}");
        carInfoFeignEntity.regInspect(dto);
        log.info("Success request InspectInfo");

        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("검수 요청 등록에 성공하였습니다.");

        return ResponseDto.success("Successful registration of inspection request", successCode);
    }
    @Transactional
    public ResponseDto<SuccessCodeDto> resInspect(InspectDto dto) {
//        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}");
        carInfoFeignEntity.resultInsepct(dto);
        log.info("Success registry InspectInfo");

        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("검수 결과 등록에 성공하였습니다.");

        return ResponseDto.success("Successful registration of inspection results", successCode);
    }

    @Transactional
    public ResponseDto<InspectDto> getInspec(Integer id) {
        InspectDto exist = carInfoFeignEntity.getInspect(id);

        log.info("Valid get Inspect Info");

        return ResponseDto.success("Get Contract Successfully", exist);
    }

    @Transactional
    public ResponseDto<List<InspectDto>> getAllInspec() {
        List<InspectDto> exist = carInfoFeignEntity.getAllInspect();
//        if (dto.getCarNumber() != null) {
//            exist = carInfoRepository.findByCarNumber(dto.getCarNumber());
//            log.info("Checking is valid contract -car number : {}", dto.getCarNumber());
//        }
//
//
//        if (exist == null) {
//            log.error("Invalid contract");
//            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "CONTRACT-002", "존재하지 않는 차량 거래 입니다.");
//        }

        log.info("Valid get Inspect Info");

        return ResponseDto.success("Get Inspect Info Successfully", exist);
    }
}
