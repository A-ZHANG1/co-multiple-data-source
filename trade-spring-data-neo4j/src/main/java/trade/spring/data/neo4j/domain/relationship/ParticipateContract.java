package trade.spring.data.neo4j.domain.relationship;

import org.neo4j.ogm.annotation.*;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.node.contract.Role;

/**
 * Created by huangtao on 2019-04-22.
 */

@lombok.Data
@RelationshipEntity(type = "PARTICIPATE_CONTRACT")
public class ParticipateContract {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    Company company;

    @EndNode
    Contract contract;

    Role role;

}
