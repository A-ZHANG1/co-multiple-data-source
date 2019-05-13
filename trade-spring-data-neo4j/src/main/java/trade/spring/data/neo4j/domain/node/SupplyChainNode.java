package trade.spring.data.neo4j.domain.node;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import trade.spring.data.neo4j.common.Utils;

/**
 * SupplyChain.java 结构不太能理解，我重开一个
 *
 * Created by huangtao on 2019-05-05.
 */

@lombok.Data
@NodeEntity
public class SupplyChainNode {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String lastUpdateTime = Utils.getTimeStamp();

    private int memberCounting;

}
