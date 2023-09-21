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
import pnu.cse.cloudchain.auth.config.JwtTokenProvider;
import pnu.cse.cloudchain.auth.dto.response.*;
import pnu.cse.cloudchain.auth.dto.request.SignRequestDto;
import pnu.cse.cloudchain.auth.exception.CustomException;
import pnu.cse.cloudchain.auth.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.auth.entity.UserInfoEntity;
import pnu.cse.cloudchain.auth.repository.SignRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthControl {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SignRepository signRepository;

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
        log.error("503-1 - ModifyBoundary  -  ModifyPassword  - ServiceUnavailable -");

        String accessToken = jwtTokenProvider.createToken(account.getUserid(), account.getName());
        log.info("Created accessToken = {}", accessToken);
        String refreshToken = jwtTokenProvider.CreateRefreshToken(account.getUserid(), account.getName());
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
                .build();
        response.setData(res);


        return new ResponseEntity<ResponseDataDto>(response, headers, HttpStatus.valueOf(200));
    }

    @Transactional
    public ResponseCodeDto modifyPassword(SignRequestDto dto) {
        UserInfoEntity exist = signRepository.findByUserid(dto.getId());

        if (exist == null)
            throw new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND,"AUTH-007", "아이디를 찾을 수 없습니다.");

        exist.setPassword(passwordEncoder.encode(dto.getPassword()));
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

//    @Transactional
//    public ResponseEntity<ResponseProfileDto> getProfile(RequestProfileDto request) {
//        UserInfo account = signRepository.findByUserid(request.getUserid());
//
//        if (account == null)
//            throw new CustomException(CustomExceptionStatus.USERID_NOT_FOUND, "AUTH-002", "존재하지 않는 아이디 입니다.");
//        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
//            throw new CustomException(CustomExceptionStatus.WRONG_PASSWORD, "AUTH-002", "잘못된 비밀번호 입니다.");
//        }
//
//        String refreshToken = jwtTokenProvider.CreateRefreshToken(account.getUserid(), account.getName());
//        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
//                // expires in 1 day
//                .maxAge(24*60*60)
//                .secure(true)
//                .httpOnly(true)
//                .path("/")
//                .build();
//
//        ResponseDataDto<Object> response = new ResponseDataDto<>();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("refreshToken", cookie.toString());
//        response.setResult("SUCCESS");
//        response.setMessage("SignIn Successfully");
//        SignInResponseDto res = SignInResponseDto.builder()
//                .id(account.getUserid())
//                .name(account.getName())
//                .accessToken(jwtTokenProvider.createToken(account.getUserid(), account.getName()))
//                .build();
//        response.setData(res);
//
//
//        return new ResponseEntity<ResponseDataDto>(response, headers, HttpStatus.valueOf(200));
//    }
}
