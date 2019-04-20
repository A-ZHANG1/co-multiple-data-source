package trade.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

@lombok.Data
@NodeEntity
public class Company {

    @Id
    @GeneratedValue
    private Long id;
    private int companyId;
    private String companyName;
    private double capital;
    private double nodeWeight;
    private int core;
    private int type;

    public Company() {
    }

    public Company(Long id, int companyId, String companyName, double capital, double nodeWeight, int core, int type) {
        this.id = id;
        this.companyId = companyId;
        this.companyName = companyName;
        this.capital = capital;
        this.nodeWeight = nodeWeight;
        this.core = core;
        this.type = type;
    }
}