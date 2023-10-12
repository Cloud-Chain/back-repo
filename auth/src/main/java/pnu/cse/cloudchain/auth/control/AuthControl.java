package pnu.cse.cloudchain.auth.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pnu.cse.cloudchain.auth.config.JwtTokenProvider;
import pnu.cse.cloudchain.auth.dto.request.CertRequestDto;
import pnu.cse.cloudchain.auth.dto.response.*;
import pnu.cse.cloudchain.auth.dto.request.SignRequestDto;
import pnu.cse.cloudchain.auth.entity.UserInfoFeignEntity;
import pnu.cse.cloudchain.auth.exception.CustomException;
import pnu.cse.cloudchain.auth.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.auth.entity.UserInfoEntity;
import pnu.cse.cloudchain.auth.repository.SignRepository;
import pnu.cse.cloudchain.auth.service.S3UploadService;

import java.io.IOException;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthControl {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SignRepository signRepository;
    private final UserInfoFeignEntity userInfoFeignEntity;
    private final S3UploadService s3UploadService;

    @Transactional
    public ResponseEntity<ResponseDataDto> signIn(SignRequestDto request) {
        log.info("Checking is valid request = {}", request.getId());
        UserInfoEntity account = signRepository.findByUserid(request.getId());

        if (account == null) {
            log.error("Invalid request");
            throw new CustomException(CustomExceptionStatus.USERID_NOT_FOUND, "AUTH-002", "존재하지 않는 아이디 입니다.");
        }
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            log.error("Invalid request");
            throw new CustomException(CustomExceptionStatus.WRONG_PASSWORD, "AUTH-002", "잘못된 비밀번호 입니다.");
        }
        log.info("Valid request");

        String accessToken = jwtTokenProvider.createToken(account.getUserid(), account.getName(), account.getCert(), account.getOrg());
        log.info("Created accessToken = {}", accessToken);
        String refreshToken = jwtTokenProvider.CreateRefreshToken(account.getUserid(), account.getName(), account.getCert(), account.getOrg());
        log.info("Created refreshToken = {}", refreshToken);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                // expires in 1 day
                .maxAge(24*60*60)
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();

        ResponseDataDto<Object> response = new ResponseDataDto<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("refreshToken", cookie.toString());
        response.setResult("SUCCESS");
        response.setMessage("SignIn Successfully");
        SignInResponseDto res = SignInResponseDto.builder()
                .id(account.getUserid())
                .name(account.getName())
                .accessToken(accessToken)
                .org(account.getOrg())
                .build();
        response.setData(res);


        return new ResponseEntity<ResponseDataDto>(response, headers, HttpStatus.valueOf(200));
    }

    @Transactional
    public ResponseCodeDto modifyPassword(SignRequestDto dto) {
        UserInfoEntity exist = signRepository.findByUserid(dto.getId());

        if (exist == null)
            throw new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND,"AUTH-007", "아이디를 찾을 수 없습니다.");

        if (!exist.getEmail().equals(dto.getEmail()))
            throw new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND,"AUTH-007", "아이디를 찾을 수 없습니다.");

        CertRequestDto certDto = new CertRequestDto();
        certDto.setOrg(exist.getOrg());
        certDto.setUserID(dto.getId());
        certDto.setPassword(passwordEncoder.encode(dto.getPassword()));
        String cert = userInfoFeignEntity.enroll(certDto);

        exist.setPassword(certDto.getPassword());
        exist.setCert(cert);

        signRepository.save(exist);

        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("Edit Password Successfully");
        response.setResult("SUCCESS");
        response.setMessage("Request Done Successfully");
        response.setData(successCode);

        return response;
    }

    @Transactional
    public ResponseCodeDto modifyProfile(ProfileDto dto, MultipartFile image) {
        UserInfoEntity exist = signRepository.findByUserid(dto.getUserid());

        if (exist == null)
            throw new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND,"AUTH-007", "아이디를 찾을 수 없습니다.");

        String msg = "Modify ";
        if(dto.getName() != null && !dto.getName().equals(exist.getName())) {
            exist.setName(dto.getName());
            msg += "name ";
        }
        if(dto.getDetail() != null && !dto.getDetail().equals(exist.getDetail())) {
            exist.setDetail(dto.getDetail());
            msg += "detail ";
        }
        if(image != null && !dto.getProfileImage().equals(exist.getProfileImage())) {
            String imageUrl = null;
            try {
                imageUrl  = s3UploadService.multipartFileUpload(image, dto.getUserid()+"_profile");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (imageUrl == null)
                throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
            exist.setProfileImage(imageUrl);
            msg += "profileImage ";
        }
        msg += "Successfully";

        signRepository.save(exist);

        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage(msg);
        response.setResult("SUCCESS");
        response.setMessage("Request Done Successfully");
        response.setData(successCode);

        return response;
    }

    @Transactional
    public ResponseCodeDto delete(SignRequestDto request) {
        UserInfoEntity account = signRepository.findByUserid(request.getId());

        if (account == null)
            throw new CustomException(CustomExceptionStatus.USERID_NOT_FOUND, "AUTH-002", "존재하지 않는 아이디 입니다.");
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new CustomException(CustomExceptionStatus.WRONG_PASSWORD, "AUTH-002", "잘못된 비밀번호 입니다.");
        }
        log.error("delete processing");
        signRepository.delete(account);
        log.error("delete done");

        ResponseCodeDto<Object> response = new ResponseCodeDto<>();
        SuccessCodeDto successCode = new SuccessCodeDto();

        successCode.setIsSuccess(true);
        successCode.setCode("1000");
        successCode.setMessage("Delete account Successfully");
        response.setResult("SUCCESS");
        response.setMessage("Delete Done Successfully");
        response.setData(successCode);

        return response;
    }

    @Transactional
    public ResponseProfileDto getProfile(String id) {
        UserInfoEntity account = signRepository.findByUserid(id);

        if (account == null)
            throw new CustomException(CustomExceptionStatus.USERID_NOT_FOUND, "AUTH-002", "존재하지 않는 아이디 입니다.");


        ResponseProfileDto response = new ResponseProfileDto();
        ProfileDto profile = new ProfileDto();

        profile.setUserid(account.getUserid());
        profile.setEmail(account.getEmail());
        profile.setOrg(account.getOrg());
        profile.setName(account.getName());
        profile.setDetail(account.getDetail());
        profile.setProfileImage(account.getProfileImage());
        profile.setBusinessRegistration(account.getBusinessRegistration());
        profile.setReportHistory(account.getReportHistory());

        response.setResult("SUCCESS");
        response.setMessage("Get Profile Successfully");
        response.setData(profile);


        return response;
    }
}
