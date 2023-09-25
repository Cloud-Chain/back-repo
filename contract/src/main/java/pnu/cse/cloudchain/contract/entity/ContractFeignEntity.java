package pnu.cse.cloudchain.contract.entity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pnu.cse.cloudchain.contract.dto.ContractDto;

import java.util.List;

@FeignClient(name = "contract", url = "http://10.125.70.19:38080")
public interface ContractFeignEntity {
    @PostMapping("/tx/buy")
    ContractDto buy(@RequestBody ContractDto dto, @RequestHeader HttpHeaders httpHeaders);
    @RequestMapping(value = "/tx/{org}/compromise",
            method = RequestMethod.PATCH)
    ContractDto compromise(@PathVariable("org") String org, @RequestBody ContractDto dto, @RequestHeader HttpHeaders httpHeaders);
    @GetMapping("/tx/")
    List<ContractDto> getContract();
    @GetMapping("/tx/user/?userName={userid}")
    List<ContractDto> getContracttUser(@PathVariable("userid") String userid);
    @GetMapping("/tx/user/?vehicleRegistrationNumber={vehicleRegistrationNumber}")
    List<ContractDto> getContracttVehicle(@PathVariable("vehicleRegistrationNumber") String vehicleRegistrationNumber);
}
