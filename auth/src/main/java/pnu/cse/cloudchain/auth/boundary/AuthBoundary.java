package pnu.cse.cloudchain.auth.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.auth.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseDataDto;
import pnu.cse.cloudchain.auth.dto.request.SignRequestDto;
import pnu.cse.cloudchain.auth.control.AuthControl;
import pnu.cse.cloudchain.auth.service.ResponseService;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthBoundary {
    private final AuthControl authControl;
    private final ResponseService responseService;

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDataDto> signIn(@RequestBody @Valid SignRequestDto dto) {
        log.info("Auth Sign-in api received RequestBody = {}", dto.toString());
        return responseService.successDataResponse(authControl.signIn(dto));
    }

    @PatchMapping("/modify-password")
    public ResponseCodeDto modifyPassword(@RequestBody @Valid SignRequestDto dto) {

        return responseService.successResponse(authControl.modifyPassword(dto));
    }

    @DeleteMapping("delete")
    public ResponseCodeDto delete(@RequestBody @Valid SignRequestDto dto) {

        return responseService.successResponse(authControl.delete(dto));
    }

//    @GetMapping("get-profile")
//    public ResponseProfileDto getProfile(@RequestBody @Valid RequestProfileDto dto) {
//
//        return responseService.successProfileResponse(authService.getProfile(dto));
//    }


}
