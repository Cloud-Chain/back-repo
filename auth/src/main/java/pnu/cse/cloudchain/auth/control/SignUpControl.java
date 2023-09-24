package pnu.cse.cloudchain.auth.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.cse.cloudchain.auth.config.JwtTokenProvider;
import pnu.cse.cloudchain.auth.dto.request.BuyerDto;
import pnu.cse.cloudchain.auth.dto.request.CertRequestDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.auth.dto.request.SellerDto;
import pnu.cse.cloudchain.auth.dto.response.SuccessCodeDto;
import pnu.cse.cloudchain.auth.entity.UserInfoFeignEntity;
import pnu.cse.cloudchain.auth.exception.CustomException;
import pnu.cse.cloudchain.auth.exception.CustomExceptionStatus;
import pnu.cse.cloudchain.auth.entity.UserInfoEntity;
import pnu.cse.cloudchain.auth.repository.SignRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignUpControl {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SignRepository signRepository;
    private final UserInfoFeignEntity userInfoFeignEntity;
    private static final Logger logger = LoggerFactory.getLogger(SignUpControl.class);
    @Transactional
    public ResponseCodeDto signUpBuyer(BuyerDto dto) {
        log.info("Checking is valid id = {}", dto.getUserid());
        UserInfoEntity exist = signRepository.findByUserid(dto.getUserid());

        if (exist != null) {
            log.error("Invalid id AUTH-001");
//            logger.error("Invalid id");
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");
        }
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
    public ResponseCodeDto signUpSeller(SellerDto dto) {
        UserInfoEntity exist = signRepository.findByUserid(dto.getUserid());

        if (exist != null)
            throw new CustomException(CustomExceptionStatus.DUPLICATED_USERID, "AUTH-001", "이미 존재하는 아이디입니다.");

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
}
