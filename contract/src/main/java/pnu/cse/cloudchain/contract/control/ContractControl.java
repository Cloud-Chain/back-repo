package pnu.cse.cloudchain.contract.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseDto;
import pnu.cse.cloudchain.contract.dto.response.SuccessCodeDto;
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

    @Transactional
    public ResponseDto<SuccessCodeDto> contract(ContractDto dto) {
        log.info("Checking is valid contract - buyer id : {}, seller id : {}, carnumber : {}", dto.getBuyerid(), dto.getSellerid(), dto.getCarNumber());
        List<ContractDto> existContract = contractRepository.findByCarNumber(dto.getCarNumber());

        if (existContract != null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "CONTRACT-001", "이미 존재하는 차량 거래 입니다.");
        }
        log.info("Valid contract");

        SuccessCodeDto successCode = new SuccessCodeDto();

        ContractEntity contractEntity = ContractEntity.createContract(dto);
        contractRepository.save(contractEntity);
        log.info("Save Review in DB Successfully");
        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("거래 요청에 성공하였습니다.");

        return ResponseDto.success("Create Contract Successfully", successCode);
    }

    @Transactional
    public ResponseDto<SuccessCodeDto> schedulingContract(ContractDto dto) {
        ContractEntity exist = contractRepository.findBySelleridAndBuyeridAndCarNumber(dto.getSellerid(), dto.getBuyerid(), dto.getCarNumber());
        String msg = "Edit ";

        if (exist == null)
            throw new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND,"CONTRACT-002", "존재하지 않는 차량 거래 입니다.");
        if (dto.getContractDate() != null) {
            exist.editContractDate(dto.getContractDate());
            msg += "contractDate ";
        }
        if (dto.getContractLocation() != null) {
            exist.editContractLocation(dto.getContractLocation());
            msg += "contractLocation ";
        }
        if (dto.getContractAmount() != null) {
            exist.editContractAmount(dto.getContractAmount());
            msg += "contractAmount ";
        }
        if (dto.getContractPdate() != null) {
            exist.editContractPdate(dto.getContractPdate());
            msg += "contractPdate ";
        }
        if (dto.getContractState() != null) {
            exist.editContractState(dto.getContractState());
            msg += "contractState ";
        }
        msg += "Successfully";

        contractRepository.save(exist);

        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage(msg);

        return ResponseDto.success("Modify Contract Successfully", successCode);
    }

    @Transactional
    public ResponseDto<List<ContractDto>> getContract(ContractDto dto) {
        List<ContractDto> exist = null;
        if (dto.getSellerid() != null && dto.getCarNumber() != null) {
            exist = contractRepository.findBySelleridAndCarNumber(dto.getSellerid(), dto.getCarNumber());
            log.info("Checking is valid contract - seller id : {}, car number : {}", dto.getSellerid(), dto.getCarNumber());
        }
        else if (dto.getBuyerid() != null && dto.getCarNumber() != null) {
            exist = contractRepository.findByBuyeridAndCarNumber(dto.getBuyerid(), dto.getCarNumber());
            log.info("Checking is valid contract - buyer id : {}, car number : {}", dto.getBuyerid(),  dto.getCarNumber());
        }
        else if (dto.getCarNumber() != null) {
            exist = contractRepository.findByCarNumber(dto.getCarNumber());
            log.info("Checking is valid contract - car number : {}", dto.getCarNumber());
        }
        else if (dto.getSellerid() != null) {
            exist = contractRepository.findBySellerid(dto.getSellerid());
            log.info("Checking is valid contract - seller id : {}", dto.getSellerid());
        }
        else if (dto.getBuyerid() != null) {
            exist = contractRepository.findByBuyerid(dto.getBuyerid());
            log.info("Checking is valid contract - buyer id : {}", dto.getBuyerid());
        }


        if (exist == null) {
            log.error("Invalid contract");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "CONTRACT-002", "존재하지 않는 차량 거래 입니다.");
        }

        log.info("Valid contract");

        return ResponseDto.success("Get Contract Successfully", exist);
    }
}
