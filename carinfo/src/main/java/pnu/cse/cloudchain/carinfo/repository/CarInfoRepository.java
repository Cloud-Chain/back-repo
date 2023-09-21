package pnu.cse.cloudchain.carinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;
import pnu.cse.cloudchain.carinfo.entity.CarInfoEntity;

import java.util.List;

public interface CarInfoRepository extends JpaRepository<CarInfoEntity, String> {
    List<CarInfoDto> findByCarNumber(String carNumber);
    CarInfoEntity findCarInfoEntityByCarNumber(String carNumber);
}
