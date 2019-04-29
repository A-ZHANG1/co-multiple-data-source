package trade.spring.data.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.apiModel.contract.ApiContract;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.mysql.mapper.ContractMapper;
import trade.spring.data.neo4j.services.ContractService;

/**
 * Created by huangtao on 2019-04-22.
 */

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractMapper mysqlContractMapper;

    @PostMapping("/create")
    public Contract create(@RequestBody ApiContract contract){
        return contractService.addOne(contract);
    }

    @GetMapping("/importFromMysql")
    public GeneralResponse importContractFromMysql(){
        GeneralResponse response = new GeneralResponse();
        boolean result = contractService.importFromMysql();
        if(!result) {
            response.setStatus(2);
        }
        return response;
    }

    @GetMapping("/getByCompanyNames")
    public GeneralResponse getContractsByCompanyNames(String companyNameA, String companyNameB) {
        GeneralResponse response = new GeneralResponse();
        response.setObj(contractService.getContractsByCompanyNames(companyNameA, companyNameB));
        if(response.getObj() == null)
            response.setStatus(2);
        return response;
    }

    @GetMapping("/getRelationDetailByCompanyNames")
    public GeneralResponse getRelationDetailByCompanyNames(String companyNameA, String companyNameB, int monthNum) {
        GeneralResponse response = new GeneralResponse();
        response.setObj(contractService.getRelationDetail(companyNameA, companyNameB, monthNum));
        if(response.getObj() == null)
            response.setStatus(2);
        return response;
    }

}
