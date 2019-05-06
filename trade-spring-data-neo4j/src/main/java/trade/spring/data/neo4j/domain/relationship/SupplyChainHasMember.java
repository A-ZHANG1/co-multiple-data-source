package trade.spring.data.neo4j.domain.relationship;

import org.neo4j.ogm.annotation.*;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChain;
import trade.spring.data.neo4j.domain.node.SupplyChainNode;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.node.contract.Role;

/**
 * Created by Wayne.A.Z on 2019-04-29.
 */
@lombok.Data
@RelationshipEntity(type = "SUPPLY_CHAIN_HAS_MEMBER")
public class SupplyChainHasMember {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    SupplyChainNode supplyChain;

    @EndNode
    Company company;

    double confidence;

}
