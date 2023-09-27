package pnu.cse.cloudchain.auth.service;

import feign.QueryMap;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "openstackSwift", url = "http://10.125.70.26:38080")
public interface OpenstackSwiftService {
    @PutMapping("/v1/AUTH_fdb3422adae2475cac7558959244c770/usedcar/{object}")
    Response upload(@PathVariable("object") String object, @RequestBody byte [] image, @RequestHeader HttpHeaders httpHeaders);
}
