package trade.spring.data.neo4j.repositories;

import java.util.List;

import trade.spring.data.neo4j.domain.node.Company;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

//@RepositoryRestResource(collectionResourceRel = "company", path = "company")
public interface CompanyRepository extends Neo4jRepository<Company, Long> {

    @Query("MATCH (c:Company) RETURN c")
    List<Company> findAll();

    @Query("MATCH (c:Company) WHERE c.companyName={companyName} RETURN c")
    Company findByCompanyName(@Param("companyName") String companyName);

    @Query("MATCH (a:Company{companyName:{companyName}}) - [:PARTICIPATE_CONTRACT] -> (b:Contract) <- [:PARTICIPATE_CONTRACT] - (c:Company) RETURN c")
    List<Company> findNeighborByCompanyName(@Param("companyName") String companyName);

}