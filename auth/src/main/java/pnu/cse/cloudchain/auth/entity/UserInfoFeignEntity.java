package pnu.cse.cloudchain.auth.entity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import pnu.cse.cloudchain.auth.dto.request.CertRequestDto;

@FeignClient(name = "auth", url = "http://223.130.161.154:8080")
public interface UserInfoFeignEntity {
    @PostMapping("/cert/enroll")
    String enroll(@RequestBody CertRequestDto dto);
}
