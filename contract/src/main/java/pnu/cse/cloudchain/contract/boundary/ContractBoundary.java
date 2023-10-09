package pnu.cse.cloudchain.contract.boundary;

import feign.Body;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.dto.request.FilterDto;
import pnu.cse.cloudchain.contract.dto.response.ContractResponseDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseDto;
import pnu.cse.cloudchain.contract.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.contract.control.ContractControl;
import pnu.cse.cloudchain.contract.service.ResponseService;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/contract")
public class ContractBoundary {
    private final ContractControl contractControl;
    private final ResponseService responseService;

    @PostMapping("/buy")
    public ResponseDto<SuccessCodeDto> buy(@RequestBody ContractDto dto, @RequestHeader("userid") String userid, @RequestHeader("causer") String causer) {
        log.info("user id = {}  ,  causer = {}", userid, causer);
        return responseService.successResponse(contractControl.buy(dto, userid, causer));
    }

    @PatchMapping("/compromise")
    public ResponseDto<SuccessCodeDto> compromise(@RequestBody ContractDto dto, @RequestHeader("userid") String userid, @RequestHeader("causer") String causer, @RequestHeader("org") String org) {
        log.info("user id = {}  ,  causer = {} , org = {}", userid, causer, org);
        return responseService.successResponse(contractControl.compromise(dto, userid, causer, org));
    }

    @PostMapping("/get-contract")
    public ResponseDto<List<ContractResponseDto>> getContract(@RequestBody FilterDto dto) {

        return responseService.successDataResponse(contractControl.getContract(dto));
    }

    @GetMapping("/get-contract-user")
    public ResponseDto<List<ContractDto>> getContractUser(@RequestParam("userid") String userid) {

        return responseService.successDatasResponse(contractControl.getContractUser(userid));
    }

    @GetMapping("/get-contract-vehicle")
    public ResponseDto<List<ContractDto>> getContractVehicle(@RequestParam("vehicle") String vehicle) {

        return responseService.successDatasResponse(contractControl.getContractVehicle(vehicle));
    }

    @PutMapping("/image-upload")
    public ResponseCodeDto imageUpload(@RequestPart("image") MultipartFile input, @RequestHeader("userid") String userid) {
        log.info("Recevied image upload api");
        log.info(input.getContentType().toString());
        return responseService.successResponse(contractControl.imageUpload(input, userid));
    }
}
