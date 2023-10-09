package pnu.cse.cloudchain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(nullable = false)
    private String buyerName;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false)
    private String carNumber;

    @Column(nullable = true)
    private String contractDate;

    @Column(nullable = true)
    private String contractAmount;

    @Column(nullable = true)
    private String contractLocation;

    @Column(nullable = true)
    private String contractState;

    @Column(nullable = true)
    private String contractPdate;
}
