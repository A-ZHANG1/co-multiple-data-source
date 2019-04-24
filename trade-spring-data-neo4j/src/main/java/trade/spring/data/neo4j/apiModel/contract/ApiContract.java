package trade.spring.data.neo4j.apiModel.contract;

import trade.spring.data.neo4j.domain.node.contract.ContractType;

import java.util.List;

/**
 * 合同
 *
 * Created by huangtao on 2019-04-22.
 */

@lombok.Data
public class ApiContract {

    private String contractId;

    private String startTime;

    private String endTime;

    private String location;

    private List<ContractCompany> involvedCompany;

    private List<Integer> involvedFinanceActivities;

    private String tradeActivities;

    private double amount;

    private String name;

    private ContractType contractType;

}
