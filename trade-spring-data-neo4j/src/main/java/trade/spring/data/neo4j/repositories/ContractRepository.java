package trade.spring.data.neo4j.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import trade.spring.data.neo4j.domain.Contract;

/**
 * Created by huangtao on 2019-04-22.
 */
public interface ContractRepository extends Neo4jRepository<Contract, Long> {
}
