package trade.spring.data.neo4j.services;

import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.repositories.ContractRepository;

/**
 * Created by huangtao on 2019-04-22.
 */

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


}
