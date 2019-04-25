package trade.spring.data.neo4j.apiModel.graph;

import trade.spring.data.neo4j.domain.node.Company;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by huangtao on 2019-04-25.
 */

@lombok.Data
public class SubGraph {

    private Set<Company> nodes;

    private Set<Link> links;

    public SubGraph() {
        nodes = new HashSet<>();
        links = new HashSet<>();
    }

}
