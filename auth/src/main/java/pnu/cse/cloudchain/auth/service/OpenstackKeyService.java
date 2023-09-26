package pnu.cse.cloudchain.auth.service;

import feign.QueryMap;
import feign.Response;
import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "openstackKey", url = "http://10.125.70.26:30080")
public interface OpenstackKeyService {
    @PostMapping("/identity/v3/auth/tokens")
    Response key(JSONObject dto);
}
