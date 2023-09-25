package pnu.cse.cloudchain.contract.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseDto;
import pnu.cse.cloudchain.contract.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.contract.entity.ContractFeignEntity;
import pnu.cse.cloudchain.contract.exception.CustomException;
import pnu.cse.cloudchain.contract.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.contract.entity.ContractEntity;
import pnu.cse.cloudchain.contract.repository.ContractRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractControl {
    private final ContractRepository contractRepository;
    private final ContractFeignEntity contractFeignEntity;

    @Transactional
    public ResponseDto<SuccessCodeDto> buy(ContractDto dto, String userid, String causer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("org","buyer");
        httpHeaders.set("userID",userid);
        httpHeaders.set("CA-User",causer);

        contractFeignEntity.buy(dto, httpHeaders );
        log.info("Purchase request successful");

        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("차량 구매 요청에 성공하였습니다.");

        return ResponseDto.success("Purchase request successful", successCode);
    }

    @Transactional
    public ResponseDto<SuccessCodeDto> compromise(ContractDto dto, String userid, String causer, String org) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("org",org);
        httpHeaders.set("userID",userid);
        httpHeaders.set("CA-User",causer);

        contractFeignEntity.compromise(org, dto, httpHeaders );
        log.info("Compromise request successful");

        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("차량 거래 조정 요청에 성공하였습니다.");

        return ResponseDto.success("Compromise request successful", successCode);
    }

    @Transactional
    public ResponseDto<List<ContractDto>> getContract() {

        List<ContractDto> data = contractFeignEntity.getContract();
        log.info("Valid get Inspect Info");

        return ResponseDto.success("Purchase request successful", data);
    }

    @Transactional
    public ResponseDto<List<ContractDto>> getContractUser(String userid) {

        List<ContractDto> data = contractFeignEntity.getContracttUser(userid);
        log.info("Valid get Inspect Info");

        return ResponseDto.success("Purchase request successful", data);
    }

    @Transactional
    public ResponseDto<List<ContractDto>> getContractVehicle(String vehicle) {

        List<ContractDto> data = contractFeignEntity.getContracttVehicle(vehicle);
        log.info("Valid get Inspect Info");

        return ResponseDto.success("Purchase request successful", data);
    }

}
