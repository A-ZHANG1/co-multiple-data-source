package trade.spring.data.neo4j.mysql.mapper;

import org.apache.ibatis.annotations.*;
import trade.spring.data.neo4j.mysql.model.Company;
import trade.spring.data.neo4j.mysql.model.Link;

import java.util.List;

/**
 * Created by Wayne.A.Z on 2019/1/20.
 */
@Mapper
public interface LinkMapper {
    @Select("select * from Links")
    List<Link> getAllLinks();

    @Insert("insert into Links (partyAName,partyBName,linkWeight) values (#{partyAName},#{partyBName},#{linkWeight})")
    @Options(useGeneratedKeys = true, keyProperty = "linkId", keyColumn = "linkId")
    int createLink(Link link);

    @Select("select * from Links where partyAName=#{partyAName} and partyBName=#{partyBName}")
    List<Link> getLinksByAB(@Param("partyAName") String partyAName, @Param("partyBName") String partyBName);

    @Update("update Links SET linkWeight=#{linkWeight} where linkId=#{linkId}")
    void updateLink(Link link);

    @Select("select * from Links where partyAName=#{companyName}")
    List<Link> getLinksStartFromC(Company c);

    @Select("select * from Links where partyBName=#{companyName}")
    List<Link> getLinksPointToC(Company c);

    @Select("select * from Links where partyAName=#{companyName} or partyBName=#{companyName}")
    List<Link> getLinkedNodesOfC(Company c);
}
