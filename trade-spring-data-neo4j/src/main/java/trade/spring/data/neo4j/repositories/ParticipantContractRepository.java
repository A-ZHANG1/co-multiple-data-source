package trade.spring.data.neo4j.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;

/**
 * Created by huangtao on 2019-04-22.
 */
public interface ParticipantContractRepository extends Neo4jRepository<ParticipateContract, Long> {
}
