package trade.spring.data.neo4j.domain.relationship;

import org.neo4j.ogm.annotation.*;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChain;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.node.contract.Role;

/**
 * Created by Wayne.A.Z on 2019-04-29.
 */
@lombok.Data
@RelationshipEntity(type = "HAS_MEMBER")
public class HasMember {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    SupplyChain supplyChain;

    @EndNode
    Company company;

}
