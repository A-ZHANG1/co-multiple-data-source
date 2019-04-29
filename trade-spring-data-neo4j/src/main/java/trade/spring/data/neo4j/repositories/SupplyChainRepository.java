package trade.spring.data.neo4j.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChain;

import java.util.List;

/**
 * Created by Wayne.A.Z on 2019-04-29.
 */
public interface SupplyChainRepository extends Neo4jRepository<SupplyChain, Long> {
    @Query("CREATE (subGraph:SupplyChain) RETURN subGraph")
    List<Company> insertSupplyChain(@Param("subGraph") SupplyChain subGraph);
}
