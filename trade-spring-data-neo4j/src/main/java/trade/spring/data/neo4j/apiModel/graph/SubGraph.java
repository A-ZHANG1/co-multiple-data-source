package trade.spring.data.neo4j.apiModel.graph;

import trade.spring.data.neo4j.domain.node.Company;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by huangtao on 2019-04-25.
 */

@lombok.Data
public class SubGraph {

    private Long id;

    private Set<Node> nodes;

    private Set<Link> links;

    public SubGraph() {
        nodes = new HashSet<>();
        links = new HashSet<>();
    }

    public void addLink(Link l){
        if(this.links.contains(l)){
            for(Link a: this.links){
                if(a.equals(l)){
                    a.setLinkWeight(l.getLinkWeight() + a.getLinkWeight());
                }
            }
        }else{
            this.links.add(l);
        }
    }

    public void appendGraph(SubGraph sg){
        this.nodes.addAll(sg.getNodes());
        this.links.addAll(sg.getLinks());
    }
}
