package pnu.cse.cloudchain.carinfo.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pnu.cse.cloudchain.carinfo.control.CarInfoControl;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.InspectDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.carinfo.service.ResponseService;
import pnu.cse.cloudchain.carinfo.service.S3UploadService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/car-info")
public class CarInfoBoundary {
    private final CarInfoControl carInfoControl;
    private final ResponseService responseService;
    private final S3UploadService s3UploadService;

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
    @PatchMapping(value="/inspec", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<SuccessCodeDto> resInspec(@RequestPart("dto") InspectDto dto, @RequestPart("imagesRequest") List<MultipartFile> imagesRequest) {

        return responseService.successResponse(carInfoControl.resInspect(dto, imagesRequest));
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

    @PutMapping("/image-upload")
    public String imageUpload(@RequestPart("image") MultipartFile input, @RequestHeader("userid") String userid) throws IOException {
        log.info("Recevied image upload api");
        log.info(input.getContentType().toString());
        return s3UploadService.multipartFileUpload(input, userid);
    }
}
