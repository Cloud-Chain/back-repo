package pnu.cse.cloudchain.carinfo.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.carinfo.entity.CarInfoEntity;
import pnu.cse.cloudchain.carinfo.exception.CustomException;
import pnu.cse.cloudchain.carinfo.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.carinfo.repository.CarInfoRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarInfoControl {
    private final CarInfoRepository carInfoRepository;

    @Transactional
    public ResponseDto<SuccessCodeDto> regCar(CarInfoDto dto) {
        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}", dto.getModel(), dto.getPrice(), dto.getCarNumber());
        CarInfoEntity existCarInfo = carInfoRepository.findCarInfoEntityByCarNumber(dto.getCarNumber());

        if (existCarInfo != null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "CONTRACT-001", "이미 존재하는 차량 거래 입니다.");
        }
        log.info("Valid contract");

        SuccessCodeDto successCode = new SuccessCodeDto();

        CarInfoEntity carInfoEntity = CarInfoEntity.createCarInfo(dto, "carInfo");
        carInfoRepository.save(carInfoEntity);
        log.info("Save Review in DB Successfully");
        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("거래 요청에 성공하였습니다.");

        return ResponseDto.success("Create Contract Successfully", successCode);
    }

    @Transactional
    public ResponseDto<SuccessCodeDto> regInspec(CarInfoDto dto) {
        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}", dto.getModel(), dto.getPrice(), dto.getCarNumber());
        CarInfoEntity existCarInfo = carInfoRepository.findCarInfoEntityByCarNumber(dto.getCarNumber());

        if (existCarInfo == null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "CONTRACT-001", "이미 존재하는 차량 거래 입니다.");
        }
        log.info("Valid contract");
        if (dto.getInspectState() == true) {
            existCarInfo.editInspectState(true);
            carInfoRepository.save(existCarInfo);
        }

        SuccessCodeDto successCode = new SuccessCodeDto();

        CarInfoEntity carInfoEntity = CarInfoEntity.createCarInfo(dto, "inspecInfo");
        carInfoRepository.save(carInfoEntity);
        log.info("Save Review in DB Successfully");
        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("거래 요청에 성공하였습니다.");

        return ResponseDto.success("Create Contract Successfully", successCode);
    }

    @Transactional
    public ResponseDto<List<CarInfoDto>> getCar(CarInfoDto dto) {
        List<CarInfoDto> exist = null;
        if (dto.getModel() != null && dto.getCarNumber() != null) {
//            exist = carInfoRepository.findBySelleridAndCarNumber(dto.getSellerid(), dto.getCarNumber());
//            log.info("Checking is valid contract - seller id : {}, car number : {}", dto.getSellerid(), dto.getCarNumber());
        }
        else if (dto.getPrice() != null && dto.getCarNumber() != null) {
//            exist = carInfoRepository.findByBuyeridAndCarNumber(dto.getBuyerid(), dto.getCarNumber());
//            log.info("Checking is valid contract - buyer id : {}, car number : {}", dto.getBuyerid(),  dto.getCarNumber());
        }



        if (exist == null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "CONTRACT-002", "존재하지 않는 차량 거래 입니다.");
        }

        log.info("Valid contract");

        return ResponseDto.success("Get Contract Successfully", exist);
    }

    @Transactional
    public ResponseDto<List<CarInfoDto>> getInspec(CarInfoDto dto) {
        List<CarInfoDto> exist = null;
        if (dto.getCarNumber() != null) {
            exist = carInfoRepository.findByCarNumber(dto.getCarNumber());
            log.info("Checking is valid contract -car number : {}", dto.getCarNumber());
        }


        if (exist == null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "CONTRACT-002", "존재하지 않는 차량 거래 입니다.");
        }

        log.info("Valid contract");

        return ResponseDto.success("Get Contract Successfully", exist);
    }
}
