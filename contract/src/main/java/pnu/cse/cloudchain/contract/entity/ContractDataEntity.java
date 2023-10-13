package pnu.cse.cloudchain.contract.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.cse.cloudchain.contract.dto.ContractDto;
import pnu.cse.cloudchain.contract.dto.request.FilterDto;
import pnu.cse.cloudchain.contract.dto.response.ContractResponseDto;
import pnu.cse.cloudchain.contract.dto.response.ResponseDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractDataEntity {
    private final ContractFeignEntity contractFeignEntity;
    @Transactional
    public List<ContractDto> getContract() {
        List<ContractDto> data = contractFeignEntity.getContract();

        log.info("Received Get-Contract Api Contract Entity.");
        log.info("Data \n{}", data.toString());

        return data;
    }
}
