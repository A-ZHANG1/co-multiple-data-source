package trade.spring.data.neo4j.proximitySearch;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;

/**
 * Created by Wayne.A.Z on 2019-05-20.
 */
public class IdGraph {
    SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph;
    ArrayList<Integer> nodeList;

    public IdGraph() {
    }

    public IdGraph(SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph, ArrayList<Integer> node) {
        this.graph = graph;
        this.nodeList = node;
    }

    public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    public void setGraph(
            SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public ArrayList<Integer> getNodeList() {
        return nodeList;
    }
}
