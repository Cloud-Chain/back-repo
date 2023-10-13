package pnu.cse.cloudchain.auth.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pnu.cse.cloudchain.auth.dto.request.BuyerDto;
import pnu.cse.cloudchain.auth.dto.response.ProfileDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseCodeDto;
import pnu.cse.cloudchain.auth.dto.response.ResponseDataDto;
import pnu.cse.cloudchain.auth.dto.request.SignRequestDto;
import pnu.cse.cloudchain.auth.control.AuthControl;
import pnu.cse.cloudchain.auth.dto.response.ResponseProfileDto;
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

    @PostMapping("/modify-password")
    public ResponseCodeDto modifyPassword(@RequestBody @Valid SignRequestDto dto) {

        return responseService.successResponse(authControl.modifyPassword(dto));
    }

    @PatchMapping(value="/modify-profile", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseCodeDto modifyProfile(@RequestPart("dto") ProfileDto dto, @RequestPart(value = "image", required = false) MultipartFile image) {

        return responseService.successResponse(authControl.modifyProfile(dto, image));
    }

    @DeleteMapping("delete")
    public ResponseCodeDto delete(@RequestBody @Valid SignRequestDto dto) {

        return responseService.successResponse(authControl.delete(dto));
    }

    @GetMapping("get-profile")
    public ResponseProfileDto getProfile(@RequestParam("userid") String id) {

        return responseService.successProfileResponse(authControl.getProfile(id));
    }
    @GetMapping("get-profile-name")
    public ResponseProfileDto getProfileByName(@RequestParam("username") String username) {

        return responseService.successProfileResponse(authControl.getProfileByName(username));
    }

}
