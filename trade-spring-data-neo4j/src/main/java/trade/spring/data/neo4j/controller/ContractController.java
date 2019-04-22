package trade.spring.data.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trade.spring.data.neo4j.apiModel.contract.ApiContract;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.services.ContractService;

/**
 * Created by huangtao on 2019-04-22.
 */

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/create")
    public Contract create(@RequestBody ApiContract contract){
        return contractService.addOne(contract);
    }

}
