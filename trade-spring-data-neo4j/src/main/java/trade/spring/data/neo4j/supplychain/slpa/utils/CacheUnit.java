package trade.spring.data.neo4j.supplychain.slpa.utils;

import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.contract.Contract;

import java.util.List;

/**
 * Created by huangtao on 2019-05-13.
 */

@lombok.Data
public class CacheUnit {

    Contract contract;

    List<Company> companies;

    public CacheUnit(Contract contract, List<Company> companies) {
        this.contract = contract;
        this.companies = companies;
    }
}
