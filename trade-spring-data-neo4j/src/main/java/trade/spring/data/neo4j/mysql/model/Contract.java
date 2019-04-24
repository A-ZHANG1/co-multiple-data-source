package trade.spring.data.neo4j.mysql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Wayne.A.Z on 2019/1/18.
 */
@Getter
@Setter
@NoArgsConstructor
public class Contract {
    private int contractId;

    private String partyAName;

    private String partyBName;

    private double amount;

    private String startTime;

    private String endTime;

    private String location;

    private String type;

    public Contract(String partyAName, String partyBName, double amount, String startTime, String endTime, String location,String type) {
        this.partyAName = partyAName;
        this.partyBName = partyBName;
        this.amount = amount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.type = type;
    }
}
