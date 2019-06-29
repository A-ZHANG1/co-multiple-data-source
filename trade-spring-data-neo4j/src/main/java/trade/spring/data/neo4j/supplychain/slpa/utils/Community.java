package trade.spring.data.neo4j.supplychain.slpa.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangtao on 2019-05-05.
 */

@lombok.Data
public class Community {

    private int id;

    private Map<Integer, Double> members = new HashMap<>();

    public boolean cover(Community community) {
        for (int memberId : community.members.keySet()) {
            if (!members.containsKey(memberId))
                return false;
        }
        return true;
    }

}
