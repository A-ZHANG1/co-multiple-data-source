package trade.spring.data.neo4j.supplychain.slpa;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;
import java.util.Map.Entry;


public class CommunityFinder {
	
	IntegerNodeGraph inGraph = new IntegerNodeGraph();

	public void initIntegerNodeGraph(SimpleDirectedWeightedGraph graph) {
		inGraph.graph = graph;
		for(int nodeId : inGraph.graph.vertexSet()){
			IntegerNode node = new IntegerNode(nodeId, nodeId);
			node.updateCommunityDistribution(nodeId, 1);
			inGraph.nodeMap.add(node);
		}
	}

	public void SLPA(int iterations) {
		for (int i = 0; i < iterations; i++) {
			//For every node in the graph
			for (IntegerNode node : inGraph.nodeMap) {
				updateLabels(node);
			}
		}
	}

	private void updateLabels(IntegerNode currentNode) {
		Set<DefaultWeightedEdge> incomingEdges = inGraph.graph.incomingEdgesOf(currentNode.id);

		HashMap<Integer, Double> incomingVotes = new HashMap<>();

		//For each vertex V with an incoming edge to the current node
		for (DefaultWeightedEdge edge : incomingEdges) {
			int speakerId = inGraph.graph.getEdgeSource(edge);
			IntegerNode speakerNode = null;
			for(IntegerNode node : inGraph.nodeMap){
				if(node.id == speakerId){
					speakerNode = node;
					break;
				}
			}

			int votedCommunity = speakerNode.speakerVote();
			double votedCommunitycount = inGraph.graph.getEdgeWeight(edge);
			if (incomingVotes.containsKey(votedCommunity))
				votedCommunitycount += incomingVotes.get(votedCommunity);

			incomingVotes.put(votedCommunity, votedCommunitycount);

		}

		//Find the most popular vote
		Iterator<Entry<Integer, Double>> it = incomingVotes.entrySet().iterator();
		int popularCommunity = -1;
		double popularCommunityCount = 0;
		while (it.hasNext()) {
			Entry<Integer, Double> entry = it.next();
			if (entry.getValue() > popularCommunityCount) {
				popularCommunity = entry.getKey();
				popularCommunityCount = entry.getValue();
			}
		}

		//Update community distribution of the current node by 1
		currentNode.updateCommunityDistribution(popularCommunity, 1);
	}

	/**
	 * 移除可能性小于threshold的标签
	 * @param threshold
	 */
	public void postProcessing(double threshold) {
		for(IntegerNode node : inGraph.nodeMap) {
			double sum = 0;
			for(int n : node.communityDistribution.values()) {
				sum += n;
			}
			List<Integer> removeLabels = new ArrayList<>();
			for(int communityId : node.communityDistribution.keySet()) {
				// System.out.println("node "+ node.id +" community " + communityId + ": " + node.communityDistribution.get(communityId) / sum);
				double confidence = node.communityDistribution.get(communityId) / sum;
				if(confidence < threshold) {
					removeLabels.add(communityId);
				}
			}

			for(int label : removeLabels) {
				node.communityDistribution.remove(label);
			}
		}
	}

	public List<Community> getCommunities() {
		List<Community> results = new ArrayList<>();

		Map<Integer, Community> communityMap = new HashMap<>();
		for(IntegerNode node : inGraph.nodeMap){
			double sum = 0;
			for(int n : node.communityDistribution.values()) {
				sum += n;
			}

			for(int communityId : node.communityDistribution.keySet()) {
				Community community = communityMap.get(communityId);
				if(community == null){
					community = new Community();
					community.setId(communityId);
					communityMap.put(communityId, community);
					results.add(community);
				}
				community.getMembers().put(node.id, node.communityDistribution.get(communityId) / sum);
			}
		}

		// remove covered community
		for (int i = 1; i < results.size(); i++) {
			Community curCommunity = results.get(i);
			for (int j = 0; j < i; ) {
				if (results.get(j).cover(curCommunity)) {
					results.remove(i);
					i--;
					for (int memberId : curCommunity.getMembers().keySet()) {
						Map<Integer, Double> map = results.get(j).getMembers();
						map.put(memberId, map.get(memberId) + curCommunity.getMembers().get(memberId));
					}
					break;
				} else if (curCommunity.cover(results.get(j))) {
					Community removeCommunity = results.remove(j);
					i--;
					for (int memberId : removeCommunity.getMembers().keySet()) {
						Map<Integer, Double> map = curCommunity.getMembers();
						map.put(memberId, map.get(memberId) + removeCommunity.getMembers().get(memberId));
					}
				} else {
					j++;
				}
			}
		}

		return results;
	}
}
