package pnu.cse.cloudchain.carinfo.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.carinfo.control.CarInfoControl;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.InspectDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.carinfo.service.ResponseService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/car-info")
public class CarInfoBoundary {
    private final CarInfoControl carInfoControl;
    private final ResponseService responseService;

    @PostMapping("/car")
    public ResponseDto<SuccessCodeDto> regCar(@RequestBody CarInfoDto dto, @RequestHeader("userid") String userid, @RequestHeader("causer") String causer) {
//        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successResponse(carInfoControl.regCar(dto, userid, causer));
    }

    @PostMapping("/inspec")
    public ResponseDto<SuccessCodeDto> regInspec(@RequestBody InspectDto dto) {
//        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successResponse(carInfoControl.regInspec(dto));
    }
    @PatchMapping("/inspec")
    public ResponseDto<SuccessCodeDto> resInspec(@RequestBody InspectDto dto) {
//        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successResponse(carInfoControl.resInspect(dto));
    }

    @GetMapping("/inspec")
    public ResponseDto<InspectDto> getInspec(@RequestParam("id") Integer id) {
        log.info("Auth Sign-in api received RequestBody = {}", id.toString());
        return responseService.successDataResponse(carInfoControl.getInspec(id));
    }

    @GetMapping("/inspec-all")
    public ResponseDto<List<InspectDto>> getAllInspec() {
//        log.info("Auth Sign-in api received RequestBody = {}");
        return responseService.successDatasResponse(carInfoControl.getAllInspec());
    }
}
