package trade.spring.data.neo4j.domain.node.contract;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import trade.spring.data.neo4j.apiModel.contract.ApiContract;

/**
 * 合同
 *
 * Created by huangtao on 2019-04-22.
 */

@lombok.Data
@NodeEntity
public class Contract {

    @Id
    @GeneratedValue
    private Long id;

    private String contractId;

    private String startTime;

    private String endTime;

    private String location;

    private String tradeActivities;

    private double amount;

    private String contractName;

    private ContractType contractType;

    public Contract(){}

    public Contract(ApiContract apiContract){
        contractId = apiContract.getContractId();
        startTime = apiContract.getStartTime();
        endTime = apiContract.getEndTime();
        location = apiContract.getLocation();
        tradeActivities = apiContract.getTradeActivities();
        amount = apiContract.getAmount();
        contractName = apiContract.getName();
        contractType = apiContract.getContractType();
    }

}
