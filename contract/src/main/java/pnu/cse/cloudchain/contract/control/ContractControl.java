package pnu.cse.cloudchain.contract.control;

import feign.Request;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseDto;
import pnu.cse.cloudchain.contract.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.contract.entity.ContractFeignEntity;
import pnu.cse.cloudchain.contract.exception.CustomException;
import pnu.cse.cloudchain.contract.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.contract.entity.ContractEntity;
import pnu.cse.cloudchain.contract.repository.ContractRepository;
import pnu.cse.cloudchain.contract.service.OpenstackKeyService;
import pnu.cse.cloudchain.contract.service.OpenstackSwiftService;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractControl {
    private final OpenstackKeyService openstackKeyService;
    private final OpenstackSwiftService openstackSwiftService;
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
