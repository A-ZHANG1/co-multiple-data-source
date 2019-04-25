package trade.spring.data.neo4j.repositories;

import java.util.List;
import java.util.Map;

import trade.spring.data.neo4j.domain.node.Company;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import trade.spring.data.neo4j.domain.node.contract.Contract;

/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

//@RepositoryRestResource(collectionResourceRel = "company", path = "company")
public interface CompanyRepository extends Neo4jRepository<Company, Long> {


    @Query("MATCH (c:Company) RETURN c")
    List<Company> findAll();

    @Query("MATCH (c:Company) WHERE c.companyName={companyName} RETURN c")
    Company findByCompanyName(@Param("companyName") String companyName);

    //    @Query("MATCH (c:Company) WHERE c.id={id} RETURN c")
    //    Company findById(@Param("id") Long id);

    @Query("MATCH (a:Company{companyName:{companyName}}) - [:PARTICIPATE_CONTRACT] -> (b:Contract) <- [:PARTICIPATE_CONTRACT] - (c:Company) RETURN c")
    List<Company> findNeighborByCompanyName(@Param("companyName") String companyName);

    @Query("MATCH (a:Company) - [r1:PARTICIPATE_CONTRACT] -> (b:Contract) <- [r2:PARTICIPATE_CONTRACT] - (c:Company) WHERE id(a)={id} RETURN r1,r2,b,c")
    List<Map<String, Object>> findNeighborById(@Param("id") Long id);

    @Query("MATCH (a:Company) - [r1:PARTICIPATE_CONTRACT] -> (b:Contract) <- [r2:PARTICIPATE_CONTRACT] - (c:Company) WHERE id(a)={id} RETURN r1,r2,b,c")
    List<Map> test(@Param("id") Long id);
}