package pnu.cse.cloudchain.carinfo.entity;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.dto.InspectDto;

import java.util.List;

@FeignClient(name = "carinfo", url = "http://10.125.70.19:38080") //, fallbackFactory = CarInfoFeignFallbackFactory.Class)
public interface CarInfoFeignEntity {
    @GetMapping("/ix/")
    List<InspectDto> getAllInspect();
    @GetMapping("/ix/inspect/?id={id}")
    InspectDto getInspect(@PathVariable("id") Integer id);
    @PostMapping("/ix/inspect")
    InspectDto regInspect(@RequestBody InspectDto dto);
    @PostMapping("/tx/sell")
    CarInfoDto regCar(@RequestBody CarInfoDto dto, @RequestHeader HttpHeaders httpHeaders);

    @RequestMapping(value = "/ix/inspect",
            method = RequestMethod.PATCH)
    InspectDto resultInsepct(@RequestBody InspectDto dto);
}
