package pnu.cse.cloudchain.carinfo.service;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "openstackSwift", url = "http://10.125.70.26:38080")
public interface OpenstackSwiftService {
    @RequestMapping(value = "/v1/AUTH_fdb3422adae2475cac7558959244c770/usedcar/{object}"
            , method = RequestMethod.PUT)
    Response upload(@PathVariable("object") String object, @RequestBody byte [] input, @RequestHeader HttpHeaders httpHeaders);
}
