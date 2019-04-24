package trade.spring.data.neo4j.mysql.mapper;

import org.apache.ibatis.annotations.*;
import trade.spring.data.neo4j.mysql.model.Contract;

import java.util.List;

/**
 * Created by Wayne.A.Z on 2019/1/18.
 */
@Mapper
public interface ContractMapper {
    //keyProperty:model中的主键名
    //keyColumn:DB中的主键名
    @Insert("insert into Contract (partyAName, partyBName, amount,startTime,endTime,location,type) values(#{partyAName}, #{partyBName}, #{amount},#{startTime},#{endTime},#{location},#{type})")
    @Options(useGeneratedKeys = true, keyProperty = "contractId", keyColumn = "contractId")
    int createContract(Contract contract);

    @Select("select * from Contract where contractId=#{contractId}")
    List<Contract> getContractByContractId(@Param("contractId") String contractId);

    @Select("select * from Contract where partyAName=#{companyName} or partyBName=#{companyName}")
    List<Contract> getContractsOfCompanyByCompanyName(@Param("companyName") String companyName);

    @Select("select SUM(*) from Contract where partyAName=#{companyName} or partyBName=#{companyName}")
    int countContractsOfCompanyByCompanyName(@Param("companyName") String companyName);


    @Select("select * from Contract")
    List<Contract> getAllContracts();
}
