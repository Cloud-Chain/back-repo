package pnu.cse.cloudchain.carinfo.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.carinfo.control.CarInfoControl;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
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
    public ResponseDto<SuccessCodeDto> regCar(@RequestBody @Valid CarInfoDto dto) {
        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successResponse(carInfoControl.regCar(dto));
    }

    @PostMapping("/inspec")
    public ResponseDto<SuccessCodeDto> regInspec(@RequestBody @Valid CarInfoDto dto) {
        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successResponse(carInfoControl.regInspec(dto));
    }

    @GetMapping("/car")
    public ResponseDto<List<CarInfoDto>> getCar(@RequestBody @Valid CarInfoDto dto) {
        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successDataResponse(carInfoControl.getCar(dto));
    }

    @GetMapping("/inspec")
    public ResponseDto<List<CarInfoDto>> getInspec(@RequestBody @Valid CarInfoDto dto) {
        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successDataResponse(carInfoControl.getInspec(dto));
    }
}
