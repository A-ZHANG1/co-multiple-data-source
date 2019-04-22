package trade.spring.data.neo4j.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.apiModel.contract.ApiContract;
import trade.spring.data.neo4j.apiModel.contract.ContractCompany;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;
import trade.spring.data.neo4j.repositories.ContractRepository;

import java.util.List;

/**
 * Created by huangtao on 2019-04-22.
 */

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ParticipateContractService participateContractService;

    /**
     * 添加一个合同，以及相关的企业节点、边
     * TODO 应该是个原子操作
     *
     * @param apiContract
     * @return
     */
    public Contract addOne(ApiContract apiContract){
        Contract endpoint = contractRepository.save(new Contract(apiContract));
        if(endpoint == null)
            // save failed
            return null;

        // 每一个参与公司，加一条边
        for(ContractCompany contractCompany : apiContract.getInvolvedCompany()){
            Company startpoint = find(contractCompany.getName());
            if(startpoint == null){
                // 如果公司节点不存在，就新建一个
                startpoint = companyService.addCompany(contractCompany.getName());
                if(startpoint == null)
                    return null;
            }

            ParticipateContract participateContract = new ParticipateContract();
            participateContract.setRole(contractCompany.getRole());
            participateContract.setCompany(startpoint);
            participateContract.setContract(endpoint);
            participateContract = participateContractService.addParticipateContractRelationship(participateContract);
            if(participateContract == null) {
                return null;
            }
        }
        return endpoint;
    }

    private Company find(String name){
        List<Company> companyList = companyService.findAllCompanies();
        for(Company company : companyList){
            if(name.equals(company.getCompanyName()))
                return company;
        }
        return null;
    }

}
