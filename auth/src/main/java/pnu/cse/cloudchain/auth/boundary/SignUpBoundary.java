package pnu.cse.cloudchain.auth.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pnu.cse.cloudchain.auth.dto.request.BuyerDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.auth.dto.request.SellerDto;
import pnu.cse.cloudchain.auth.service.ResponseService;
import pnu.cse.cloudchain.auth.control.SignUpControl;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/sign-up")
public class SignUpBoundary {
    private final SignUpControl signUpControl;
    private final ResponseService responseService;

    @PostMapping("/buyer")
    public ResponseCodeDto buyer(@RequestBody @Valid BuyerDto dto) {
        log.info("Buyer Sign-up api received RequestBody = {}", dto.toString());
        return responseService.successResponse(signUpControl.signUpBuyer(dto));
    }

    @PostMapping("/seller")
    public ResponseCodeDto seller(@RequestBody SellerDto dto) {

        return responseService.successResponse(signUpControl.signUpSeller(dto));
    }
}
