package trade.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

/**
 * Created by huangtao on 2019-04-22.
 */

@lombok.Data
@NodeEntity
public class Contract {

    @Id
    @GeneratedValue
    private Long id;
    private List<Integer> involvedCompany;
    private List<Integer> involvedFinanceInstitution;
    private String tradeActivities;

}
