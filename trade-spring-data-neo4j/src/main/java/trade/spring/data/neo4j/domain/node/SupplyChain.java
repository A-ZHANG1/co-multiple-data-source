package trade.spring.data.neo4j.domain.node;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import trade.spring.data.neo4j.apiModel.graph.Link;
import trade.spring.data.neo4j.apiModel.graph.Node;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;

import java.util.Set;

/**
 * Created by Wayne.A.Z on 2019-04-29.
 */

@lombok.Data
@NodeEntity

public class SupplyChain {
    @Id
    @GeneratedValue
    private Long id;

    private Long subGraphId;

    private Set<Node> nodes;

    private Set<Link> links;

    public SupplyChain(SubGraph subGraph){
        this.nodes = subGraph.getNodes();
        this.links = subGraph.getLinks();
    }

    public SupplyChain(Long id, Long subGraphId, Set<Node> nodes, Set<Link> links) {
        this.id = id;
        this.subGraphId = subGraphId;
        this.nodes = nodes;
        this.links = links;
    }

    public SupplyChain(){
    }
}
