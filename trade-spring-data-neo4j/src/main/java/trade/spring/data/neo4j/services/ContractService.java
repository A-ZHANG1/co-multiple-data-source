package trade.spring.data.neo4j.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.apiModel.TradeRelationDetail;
import trade.spring.data.neo4j.apiModel.contract.ApiContract;
import trade.spring.data.neo4j.apiModel.contract.ContractCompany;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.node.contract.Role;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;
import trade.spring.data.neo4j.mysql.mapper.ContractMapper;
import trade.spring.data.neo4j.repositories.ContractRepository;
import trade.spring.data.neo4j.supplychain.SupplyChainDiscovery;
import trade.spring.data.neo4j.supplychain.slpa.CacheUnit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Autowired
    private SupplyChainService supplyChainService;

    @Autowired
    private ContractMapper mysqlContractMapper;

    /**
     * 添加一个合同，以及相关的企业节点、边
     * 图谱数据添加第一步，把原始数据转换为参数所示格式
     * TODO 应该是个原子操作
     *
     * @param contract
     * @return
     */
    public Contract addOne(Contract contract, List<ContractCompany> involvedCompanies){
        Contract endpoint = contractRepository.save(contract);
        List<Company> companies = new ArrayList<>();
        if(endpoint == null)
            // save failed
            return null;

        // 每一个参与公司，加一条边
        for(ContractCompany contractCompany : involvedCompanies){
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

            companies.add(startpoint);
        }

        // 异步通知检查供应链结构更新
        CacheUnit cacheUnit = new CacheUnit(endpoint, companies);
        supplyChainService.addCacheUnit(cacheUnit);

        return endpoint;
    }

    public Contract addOne(ApiContract apiContract){
        return addOne(new Contract(apiContract), apiContract.getInvolvedCompany());
    }

    private Company find(String name){
        List<Company> companyList = companyService.findAllCompanies();
        for(Company company : companyList){
            if(name.equals(company.getCompanyName()))
                return company;
        }
        return null;
    }

    public boolean importFromMysql(){
        List<trade.spring.data.neo4j.mysql.model.Contract> origins = mysqlContractMapper.getAllContracts();
        for(trade.spring.data.neo4j.mysql.model.Contract origin : origins){
            Contract contract = Contract.buildFromMysql(origin);

            List<ContractCompany> companies = new ArrayList<>();
            companies.add(new ContractCompany(null, origin.getPartyAName(), Role.PartyA));
            companies.add(new ContractCompany(null, origin.getPartyBName(), Role.PartyB));

            if(addOne(contract, companies) == null)
                return false;
        }
        return true;
    }

    public List<Contract> getContractsByCompanyNames(String nameA, String nameB) {
        return contractRepository.findContractsByCompanyNames(nameA, nameB);
    }

    public TradeRelationDetail getRelationDetail(String nameA, String nameB, int monthNum) {
        TradeRelationDetail tradeRelationDetail = new TradeRelationDetail();
        tradeRelationDetail.setContracts(getContractsByCompanyNames(nameA, nameB));

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        List<String> xList = new ArrayList<>(monthNum);
        for (int i = 0; i < monthNum; i++) {
            String str = year + "-" + (month < 10 ? ("0" + month) : month);
            xList.add(0, str);
            month--;
            if (month == 0) {
                year--;
                month = 12;
            }
        }
        tradeRelationDetail.setFrequencyX(xList);

        List<Integer> valueList = new ArrayList<>();
        for (int i = 0; i < monthNum; i++) {
            valueList.add(0);
        }
        for(Contract contract : tradeRelationDetail.getContracts()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                calendar.setTime(sdf.parse(contract.getStartTime()));
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                String str = year + "-" + (month < 10 ? ("0" + month) : month);

                if(xList.contains(str)) {
                    valueList.set(xList.indexOf(str), valueList.get(xList.indexOf(str)) + 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        tradeRelationDetail.setFrequencyValue(valueList);

        return tradeRelationDetail;
    }

}
