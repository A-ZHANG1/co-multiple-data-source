package trade.spring.data.neo4j.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChain;
import trade.spring.data.neo4j.domain.node.SupplyChainNode;

import java.util.List;

/**
 * Created by Wayne.A.Z on 2019-04-29.
 */
public interface SupplyChainNodeRepository extends Neo4jRepository<SupplyChainNode, Long> {
    @Query("CREATE (subGraph:SupplyChain) RETURN subGraph")
    List<Company> insertSupplyChain(@Param("subGraph") SupplyChain subGraph);

    @Query("MATCH (n:SupplyChainNode) - [r:SUPPLY_CHAIN_HAS_MEMBER] -> (m:Company) DELETE n,r")
    void deleteAllSupplyChainNodes();
}
