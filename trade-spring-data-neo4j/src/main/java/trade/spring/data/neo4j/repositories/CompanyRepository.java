package trade.spring.data.neo4j.repositories;

import java.util.List;
import java.util.Map;

import trade.spring.data.neo4j.domain.node.Company;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import trade.spring.data.neo4j.domain.node.SupplyChainNode;
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

    @Query("MATCH (c:Company) WHERE c.companyId={companyId} RETURN c.capital")
    double getCapitalById(@Param("companyId") Long companyId);

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

    @Query("MATCH(a:Company{type:1})-[r11:PARTICIPATE_CONTRACT]->(c1:Contract)<-[r12:PARTICIPATE_CONTRACT]-" +
            "(b:Company{type:2})-[r22:PARTICIPATE_CONTRACT]->(c2:Contract)<-[r23:PARTICIPATE_CONTRACT]-" +
            "(c:Company{type:3})-[r33:PARTICIPATE_CONTRACT]->(c3:Contract)<-[r34:PARTICIPATE_CONTRACT]-" +
            "(d:Company{type:4})-[r44:PARTICIPATE_CONTRACT]->(c4:Contract)<-[r45:PARTICIPATE_CONTRACT]-" +
            "(e:Company{type:5})" +
            "RETURN DISTINCT a,b,c,d,e,c1,c2,c3,c4,r11,r12,r22,r23,r33,r34,r44,r45")
    List<Map<String, Object>> getSupplyChainType1();

    @Query("MATCH(a:Company{type:1})-[r11:PARTICIPATE_CONTRACT]->(c1:Contract)<-[r12:PARTICIPATE_CONTRACT]-" +
            "(b:Company{type:2})-[r21:PARTICIPATE_CONTRACT]->(c2:Contract)<-[r22:PARTICIPATE_CONTRACT]-" +
            "(d:Company{type:4})-[r31:PARTICIPATE_CONTRACT]->(c3:Contract)<-[r32:PARTICIPATE_CONTRACT]-" +
            "(e:Company{type:5})" +
            "RETURN DISTINCT a,b,d,e,c1,c2,c3,r11,r12,r21,r22,r31,r32")
    List<Map<String, Object>> getSupplyChainType2();

    @Query("MATCH (s:SupplyChainNode) - [:SUPPLY_CHAIN_HAS_MEMBER] -> (c:Company) WHERE id(c)={companyId} RETURN s")
    List<SupplyChainNode> findSupplyChainNodesByCompanyId(@Param("companyId") Long id);

}