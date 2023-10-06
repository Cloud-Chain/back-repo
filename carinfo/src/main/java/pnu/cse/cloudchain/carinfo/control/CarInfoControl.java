package pnu.cse.cloudchain.carinfo.control;

import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.InspectDto;
import pnu.cse.cloudchain.carinfo.dto.request.ImagesRequestDto;
import pnu.cse.cloudchain.carinfo.dto.response.ImagesResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.carinfo.dto.response.ResponseDto;
import pnu.cse.cloudchain.carinfo.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.carinfo.entity.CarInfoFeignEntity;
import pnu.cse.cloudchain.carinfo.exception.CustomException;
import pnu.cse.cloudchain.carinfo.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.carinfo.service.OpenstackKeyService;
import pnu.cse.cloudchain.carinfo.service.OpenstackSwiftService;
import pnu.cse.cloudchain.carinfo.service.S3UploadService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarInfoControl {
    private final OpenstackKeyService openstackKeyService;
    private final OpenstackSwiftService openstackSwiftService;
    private final CarInfoFeignEntity carInfoFeignEntity;
    private final S3UploadService s3UploadService;

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
//        ImagesRequestDto req = new ImagesRequestDto(testfile1, testfile2, testfile3, testfile4, testfile5, testfile6);
//        dto.setImagesRequest(req);
        dto.setImages(getImageUrl(dto.getImagesRequest(), dto.getVehicleBasicInfo().getVehicleIdentificationNumber()));

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
        log.info("Valid get Inspect Info = {}", exist.toString());
        if (exist == null || exist.size()==0)
            throw new CustomException(CustomExceptionStatus.CAR_NOT_FOUND, "404", "차량 정보가 존재하지 않습니다.");

        return ResponseDto.success("Get Inspect Info Successfully", exist);
    }

    @Transactional
    public ImagesResponseDto getImageUrl(ImagesRequestDto dto, String carId) {
        ImagesResponseDto ret = new ImagesResponseDto();
        try {
            String inside = s3UploadService.multipartFileUpload(dto.getInside(), carId+"_inside");
            log.info("Check for upload inside");

            String outside = s3UploadService.multipartFileUpload(dto.getOutside(), carId+"_outside");
            log.info("Check for upload outside");

            String front = s3UploadService.multipartFileUpload(dto.getFront(), carId+"_front");
            log.info("Check for upload front");

            String left = s3UploadService.multipartFileUpload(dto.getLeft(), carId+"_left");
            log.info("Check for upload left");

            String right = s3UploadService.multipartFileUpload(dto.getRight(), carId+"_right");
            log.info("Check for upload right");

            String back = s3UploadService.multipartFileUpload(dto.getBack(), carId+"_back");
            log.info("Check for upload back");

            ret.setInside(inside);
            ret.setOutside(outside);
            ret.setFront(front);
            ret.setLeft(left);
            ret.setRight(right);
            ret.setBack(back);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

//    @Transactional
//    public ImagesResponseDto getImageUrl(ImagesRequestDto dto, String carId) {
//        ImagesResponseDto ret = new ImagesResponseDto();
//        try {
//            ClassPathResource resource = new ClassPathResource("json/openstackKey.json");
//            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));
//
//            Response key = openstackKeyService.key(jsonObject);
//            String swiftKey = key.headers().get("X-Subject-Token").toString();
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.set("X-Auth-Token", swiftKey);
//            httpHeaders.setContentType(MediaType.valueOf(dto.getFront().getContentType()));
//            log.info("Openstack key = {}", swiftKey);
//
//            byte [] byteArr=dto.getInside().getBytes();
//            openstackSwiftService.upload(carId+"_inside", byteArr, httpHeaders);
//            log.info("Check for upload inside");
//
//            byteArr=dto.getOutside().getBytes();
//            openstackSwiftService.upload(carId+"_outside", byteArr, httpHeaders);
//            log.info("Check for upload outside");
//
//            byteArr=dto.getFront().getBytes();
//            openstackSwiftService.upload(carId+"_front", byteArr, httpHeaders);
//            log.info("Check for upload front");
//
//            byteArr=dto.getLeft().getBytes();
//            openstackSwiftService.upload(carId+"_left", byteArr, httpHeaders);
//            log.info("Check for upload left");
//
//            byteArr=dto.getRight().getBytes();
//            openstackSwiftService.upload(carId+"_right", byteArr, httpHeaders);
//            log.info("Check for upload right");
//
//            byteArr=dto.getBack().getBytes();
//            openstackSwiftService.upload(carId+"_back", byteArr, httpHeaders);
//            log.info("Check for upload back");
//
//            ret.setInside(carId+"_inside");
//            ret.setInside(carId+"_outside");
//            ret.setInside(carId+"_front");
//            ret.setInside(carId+"_left");
//            ret.setInside(carId+"_right");
//            ret.setInside(carId+"_back");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        return ret;
//    }

    @Transactional
    public ResponseCodeDto imageUpload(MultipartFile input, String userid) {
        try {
            ClassPathResource resource = new ClassPathResource("json/openstackKey.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));

            Response key = openstackKeyService.key(jsonObject);
            String swiftKey = key.headers().get("X-Subject-Token").toString();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-Auth-Token", swiftKey);
            httpHeaders.setContentType(MediaType.valueOf(input.getContentType()));
            log.info("Openstack key = {}", swiftKey);

            byte [] byteArr=input.getBytes();
            Response res = openstackSwiftService.upload(userid, byteArr, httpHeaders);
            log.info("Check for upload = {}", res.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("회원가입에 성공하였습니다.");
        response.setResult("SUCCESS");
        response.setMessage("Create Account Successfully");
        response.setData(successCode);
        return response;
    }
}
