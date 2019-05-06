package trade.spring.data.neo4j.apiModel;

import trade.spring.data.neo4j.domain.node.SupplyChainNode;
import trade.spring.data.neo4j.domain.relationship.SupplyChainHasMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangtao on 2019-05-05.
 */

@lombok.Data
public class SupplyChainStructure {

    // vertex
    List<SupplyChainNode> supplyChainNodes;

    // edge
    List<SupplyChainHasMember> supplyChainHasMembers;

    public SupplyChainStructure() {
        supplyChainNodes = new ArrayList<>();
        supplyChainHasMembers = new ArrayList<>();
    }

}
