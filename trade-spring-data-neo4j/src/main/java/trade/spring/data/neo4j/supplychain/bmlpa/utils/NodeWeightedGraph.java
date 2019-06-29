package trade.spring.data.neo4j.supplychain.bmlpa.utils;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import trade.spring.data.neo4j.supplychain.slpa.utils.IntegerNode;
import trade.spring.data.neo4j.supplychain.slpa.utils.IntegerNodeGraph;

import java.util.ArrayList;

/**
 * Created by Wayne.A.Z on 2019-06-24.
 */
public class NodeWeightedGraph {
    SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph;
    ArrayList<WeightedNode> nodeMap;

    public NodeWeightedGraph() {
        nodeMap = new ArrayList<>();
    }

    public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    public void setGraph(
            SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public ArrayList<WeightedNode> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(ArrayList<WeightedNode> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public void addNodeToMap(int index, WeightedNode node) {
        nodeMap.add(index, node);
    }

    public int getVertexCount() {
        return nodeMap.size();
    }

    public int getEdgeCount() {
        return graph.edgeSet().size();
    }

    public static void main(String arg[]) {
        NodeWeightedGraph ingraph = new NodeWeightedGraph();
        ingraph.nodeMap.add(new WeightedNode(1, 1));
        ingraph.nodeMap.get(0).id = 2;
        System.out.println(ingraph.nodeMap.get(0).id);
    }

}
