package pnu.cse.cloudchain.contract.service;

import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;
import feign.Response;
import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

@FeignClient(name = "openstackSwift", url = "http://10.125.70.26:38080")
public interface OpenstackSwiftService {
    @RequestMapping(value = "/v1/AUTH_fdb3422adae2475cac7558959244c770/usedcar/{object}", method = RequestMethod.PUT)
    Response upload(@PathVariable("object") String object, @RequestBody byte[] input,
            @RequestHeader HttpHeaders httpHeaders);
}
