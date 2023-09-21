package pnu.cse.cloudchain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pnu.cse.cloudchain.auth.dto.request.BuyerDto;
import pnu.cse.cloudchain.auth.dto.request.SellerDto;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(nullable = false, unique = true)
    private String userid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = true)
    private String businessRegistration;

    @Column(nullable = true)
    private String reportHistory;



    public static UserInfoEntity createBuyerAccount(BuyerDto dto) {

        return UserInfoEntity.builder()
                .userid(dto.getUserid())
                .password(dto.getPassword())
                .type("buyer")
                .name(dto.getName())
                .detail(dto.getDetail())
                .build();
    }

    public static UserInfoEntity createSellerAccount(SellerDto dto) {

        return UserInfoEntity.builder()
                .userid(dto.getUserid())
                .password(dto.getPassword())
                .type("seller")
                .name(dto.getName())
                .detail(dto.getDetail())
                .businessRegistration(dto.getBusinessRegistration())
                .build();
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
