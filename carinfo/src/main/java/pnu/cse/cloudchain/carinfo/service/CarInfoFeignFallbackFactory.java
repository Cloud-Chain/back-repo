package pnu.cse.cloudchain.carinfo.service;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CarInfoFeignFallbackFactory { //implements FallbackFactory<CarInfoFeignService> {
//    @Override
//    public CarInfoFeignService create(Throwable t) {
//        return new CarInfoFeignService() {
//
//        }
//    }
}
