package pnu.cse.cloudchain.contract.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseDto;
import pnu.cse.cloudchain.contract.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.contract.control.ContractControl;
import pnu.cse.cloudchain.contract.service.ResponseService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/contract")
public class ContractBoundary {
    private final ContractControl contractControl;
    private final ResponseService responseService;

    @PostMapping("/contract")
    public ResponseDto<SuccessCodeDto> contract(@RequestBody @Valid ContractDto dto) {
        log.info("Buyer Sign-up api received RequestBody = {}", dto.toString());
        return responseService.successResponse(contractControl.contract(dto));
    }

    @PatchMapping("/scheduled")
    public ResponseDto<SuccessCodeDto> schedulingContract(@RequestBody @Valid ContractDto dto) {
        log.info("Buyer Sign-up api received RequestBody = {}", dto.toString());
        return responseService.successResponse(contractControl.schedulingContract(dto));
    }

    @GetMapping("/get-contract")
    public ResponseDto<List<ContractDto>> getContract(@RequestBody @Valid ContractDto dto) {
        log.info("Buyer Sign-up api received RequestBody = {}", dto.toString());
        return responseService.successDataResponse(contractControl.getContract(dto));
    }
}
