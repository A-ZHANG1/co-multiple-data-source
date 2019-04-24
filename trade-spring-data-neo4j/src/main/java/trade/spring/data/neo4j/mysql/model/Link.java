package trade.spring.data.neo4j.mysql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Wayne.A.Z on 2019/1/20.
 */
@Getter
@Setter
@NoArgsConstructor
public class Link {
    private int linkId;

    private String partyAName;

    private String partyBName;

    private double linkWeight;

    public Link(String partyAName, String partyBName, double linkWeight) {
        this.partyAName = partyAName;
        this.partyBName = partyBName;
        this.linkWeight = linkWeight;
    }
}
