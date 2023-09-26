package pnu.cse.cloudchain.carinfo.service;

import feign.Response;
import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "openstackKey", url = "http://10.125.70.26:30080")
public interface OpenstackKeyService {
    @PostMapping("/identity/v3/auth/tokens")
    Response key(JSONObject dto);
}
