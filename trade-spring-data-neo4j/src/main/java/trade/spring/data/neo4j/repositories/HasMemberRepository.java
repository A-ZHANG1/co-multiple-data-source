package trade.spring.data.neo4j.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.relationship.HasMember;

/**
 * Created by Wayne.A.Z on 2019-04-29.
 */
public interface HasMemberRepository extends Neo4jRepository<HasMember, Long> {

}
