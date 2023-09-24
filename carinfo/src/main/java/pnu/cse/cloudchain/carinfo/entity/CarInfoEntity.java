package pnu.cse.cloudchain.carinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pnu.cse.cloudchain.carinfo.dto.CarInfoDto;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private String carNumber;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer mileage;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = true)
    private String seller;

    @Column(nullable = true)
    private String contractState;

    @Column(nullable = true)
    private Boolean inspectState;

//    public static CarInfoEntity createCarInfo(CarInfoDto dto, String type) {
//
//        return CarInfoEntity.builder()
//                .model(dto.getModel())
//                .period(dto.getPeriod())
//                .carNumber(dto.getCarNumber())
//                .type(type)
//                .mileage(dto.getMileage())
//                .price(dto.getPrice())
//                .seller(dto.getSeller())
//                .contractState(dto.getContractState())
//                .inspectState(dto.getInspectState())
//                .build();
//    }
    public void editPrice(Integer price) {
        this.price = price;
    }
    public void editContractState(String contractState) {
        this.contractState = contractState;
    }
    public void editInspectState(Boolean inspectState) {
        this.inspectState = inspectState;
    }
}
