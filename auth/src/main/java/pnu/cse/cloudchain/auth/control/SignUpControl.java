package pnu.cse.cloudchain.auth.control;

import feign.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pnu.cse.cloudchain.auth.dto.request.BuyerDto;
import pnu.cse.cloudchain.auth.dto.request.CertRequestDto;
import pnu.cse.cloudchain.auth.dto.request.SellerDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.auth.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.auth.entity.UserInfoEntity;
import pnu.cse.cloudchain.auth.entity.UserInfoFeignEntity;
import pnu.cse.cloudchain.auth.exception.CustomException;
import pnu.cse.cloudchain.auth.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.auth.repository.SignRepository;
import pnu.cse.cloudchain.auth.service.OpenstackKeyService;
import pnu.cse.cloudchain.auth.service.OpenstackSwiftService;
import pnu.cse.cloudchain.auth.service.S3UploadService;

import java.io.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignUpControl {

    private final PasswordEncoder passwordEncoder;
    private final SignRepository signRepository;
    private final UserInfoFeignEntity userInfoFeignEntity;
    private final OpenstackKeyService openstackKeyService;
    private final OpenstackSwiftService openstackSwiftService;
    private final S3UploadService s3UploadService;

    @Transactional
    public String findId(String email) {
        log.info("Checking is valid request = {}", email);
        UserInfoEntity account = signRepository.findByEmail(email);
        return account.getUserid();
    }

    @Transactional
    public ResponseCodeDto isDuplicateId(String userid) {
        UserInfoEntity exist = signRepository.findByUserid(userid);

        if (exist != null)
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");

        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("사용 가능한 아이디 입니다.");
        response.setResult("SUCCESS");
        response.setMessage("Valid Id");
        response.setData(successCode);
        return response;
    }

    @Transactional
    public ResponseCodeDto signUpBuyer(BuyerDto dto) {
        log.info("Checking is valid id = {}", dto.getUserid());
        UserInfoEntity existId = signRepository.findByUserid(dto.getUserid());
        UserInfoEntity existEmail = signRepository.findByEmail(dto.getEmail());

        if (existId != null)
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
        if (existEmail != null)
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
        log.info("Valid id");

        CertRequestDto certDto = new CertRequestDto();
        certDto.setOrg("buyer");
        certDto.setUserID(dto.getUserid());
        certDto.setPassword(dto.getPassword());
        String cert = userInfoFeignEntity.enroll(certDto);

        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
        SuccessCodeDto successCode = new SuccessCodeDto();

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserInfoEntity userAccount = UserInfoEntity.createBuyerAccount(dto, cert);
        signRepository.save(userAccount);
        log.info("Save Account in DB Successfully");
        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("회원가입에 성공하였습니다.");
        response.setResult("SUCCESS");
        response.setMessage("Create Account Successfully");
        response.setData(successCode);
        return response;
    }

    @Transactional
    public ResponseCodeDto signUpSeller(SellerDto dto, MultipartFile image) {
        UserInfoEntity existId = signRepository.findByUserid(dto.getUserid());
        UserInfoEntity existEmail = signRepository.findByEmail(dto.getEmail());

        if (existId != null)
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
        if (existEmail != null)
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");

//        imageUpload(dto.getBusinessRegistrationRequest(), dto.getUserid());
        String imageUrl = null;
        try {
            imageUrl  = s3UploadService.multipartFileUpload(image, dto.getUserid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (imageUrl == null)
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
        dto.setBusinessRegistration(imageUrl);

        CertRequestDto certDto = new CertRequestDto();
        certDto.setOrg("seller");
        certDto.setUserID(dto.getUserid());
        certDto.setPassword(dto.getPassword());
        String cert = userInfoFeignEntity.enroll(certDto);

        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
        SuccessCodeDto successCode = new SuccessCodeDto();

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserInfoEntity userAccount = UserInfoEntity.createSellerAccount(dto, cert);
        signRepository.save(userAccount);

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("회원가입에 성공하였습니다.");
        response.setResult("SUCCESS");
        response.setMessage("Create Account Successfully");
        response.setData(successCode);
        return response;
    }

//    @Transactional
//    public ResponseCodeDto imageUpload(MultipartFile image, String userid) {
//
//
//        try {
//            ClassPathResource resource = new ClassPathResource("json/openstackKey.json");
//            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));
//
//            Response key = openstackKeyService.key(jsonObject);
//            String swiftKey = key.headers().get("X-Subject-Token").toString();
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.set("X-Auth-Token", swiftKey);
//            log.info("Openstack key = {}", swiftKey);
//
//            byte [] byteArr=image.getBytes();
//            Response res = openstackSwiftService.upload(userid, byteArr, httpHeaders);
//            log.info("Check for upload = {}", res.toString());
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
//        SuccessCodeDto successCode = new SuccessCodeDto();
//
//        successCode.setIsSuccess(true);
//        successCode.setCode("1000");
//        successCode.setMessage("회원가입에 성공하였습니다.");
//        response.setResult("SUCCESS");
//        response.setMessage("Create Account Successfully");
//        response.setData(successCode);
//        return response;
//    }
}
