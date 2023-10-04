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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String org;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 1000)
    private String detail;

    @Column(nullable = false, length = 1500)
    private String cert;

    @Column(nullable = true)
    private String businessRegistration;

    @Column(nullable = true)
    private String reportHistory;



    public static UserInfoEntity createBuyerAccount(BuyerDto dto, String ca) {

        return UserInfoEntity.builder()
                .userid(dto.getUserid())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .org("buyer")
                .name(dto.getName())
                .detail(dto.getDetail())
                .cert(ca)
                .build();
    }

    public static UserInfoEntity createSellerAccount(SellerDto dto, String ca) {

        return UserInfoEntity.builder()
                .userid(dto.getUserid())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .org("seller")
                .name(dto.getName())
                .detail(dto.getDetail())
                .cert(ca)
                .businessRegistration(dto.getBusinessRegistration())
                .build();
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setCert(String cert) {
        this.cert = cert;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

}
