package trade.spring.data.neo4j.apiModel;

import trade.spring.data.neo4j.domain.node.contract.Contract;

import java.util.List;

/**
 * Created by huangtao on 2019-04-29.
 */

@lombok.Data
public class TradeRelationDetail {

    private List<Contract> contracts;

    private List<String> frequencyX;

    private List<Integer> frequencyValue;

}
