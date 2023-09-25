package pnu.cse.cloudchain.auth.service;

import feign.Response;
import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.auth.dto.request.CertRequestDto;

@FeignClient(name = "openstack", url = "http://10.125.70.26:30080")
public interface OpenstackSwiftService {
    @PostMapping("/identity/v3/auth/tokens")
    Response key(JSONObject dto);

//    @PutMapping("/v1/AUTH_fdb3422adae2475cac7558959244c770/usedcar/{object}")
    @RequestMapping(value = "/v1/AUTH_fdb3422adae2475cac7558959244c770/usedcar/{object}",
            method = RequestMethod.PUT)
    Response upload(@PathVariable("object") String object, @RequestBody String image, @RequestHeader HttpHeaders httpHeaders);

}
