package trade.spring.data.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.contract.Contract;

import java.util.List;
import java.util.Map;

/**
 * Created by huangtao on 2019-04-22.
 */
public interface ContractRepository extends Neo4jRepository<Contract, Long> {

    @Query("MATCH (a:Company{companyName:{companyNameA}}) - [:PARTICIPATE_CONTRACT] -> (b:Contract) <- [:PARTICIPATE_CONTRACT] - (c:Company{companyName:{companyNameB}}) RETURN b")
    List<Contract> findContractsByCompanyNames(@Param("companyNameA") String companyNameA, @Param("companyNameB") String companyNameB);

    @Query("MATCH (a:Company) - [:PARTICIPATE_CONTRACT] -> (b:Contract) <- [:PARTICIPATE_CONTRACT] - (c:Company) WHERE id(b)={id} RETURN a,c")
    List<Map<String, Company>> findContractParticipants(@Param("id") Long id);

}
