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

    @Query("MATCH (c:Company) WHERE c.companyName={companyName} SET c.type = {type} RETURN c")
    Company setCompanyType(@Param("companyName") String companyName, @Param("type") int type);

    @Query("MATCH(a:Company{type:1})-[:PARTICIPATE_CONTRACT]->(r1:Contract)<-[:PARTICIPATE_CONTRACT]-" +
            "(b:Company{type:2})-[:PARTICIPATE_CONTRACT]->(r2:Contract)<-[:PARTICIPATE_CONTRACT]-" +
            "(c:Company{type:3})-[:PARTICIPATE_CONTRACT]->(r3:Contract)<-[:PARTICIPATE_CONTRACT]-" +
            "(d:Company{type:4})-[:PARTICIPATE_CONTRACT]->(r4:Contract)<-[:PARTICIPATE_CONTRACT]-" +
            "(e:Company{type:5})" +
            "RETURN a,b,c,d,e,r1,r2,r3,r4")
    List<Map<String, Object>> getSupplyChain();

}