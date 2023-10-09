package pnu.cse.cloudchain.contract.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterDto {
    private  Boolean filter;
    private  Boolean priceFilter;
    private  Boolean mileageFilter;
    private  String model;
    private  String status;
    private  String assignor;
    private String periodRangeStart;
    private  String periodRangeEnd;
    private  Integer priceRangeStart;
    private  Integer priceRangeEnd;
    private  Integer mileageRangeStart;
    private  Integer mileageRangeEnd;
}
