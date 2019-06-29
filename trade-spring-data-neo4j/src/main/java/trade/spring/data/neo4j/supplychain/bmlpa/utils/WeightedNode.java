package trade.spring.data.neo4j.supplychain.bmlpa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class WeightedNode {

	int id;
	double capital;

	/*
	 *社群归属 : <community_label, belonging_coefficient(Double)>
	 *处理社群重合的情况，不使用单一社群标签标定节点社群归属
	 */
	HashMap<Integer, Double> communityDistribution;

	public WeightedNode(int id, double capital) {
		this.id = id;
		this.capital = capital;
		this.communityDistribution = new HashMap<> ();
	}

	public int getId(){
		return this.id;
	}

	public HashMap<Integer, Double> getCommunityDistribution() {
		return communityDistribution;
	}

	public void updateCommunityDistribution(int votedCommunity, double voteIncrement) {
		if (communityDistribution.containsKey(votedCommunity))
			voteIncrement += communityDistribution.get(votedCommunity);
		communityDistribution .put(votedCommunity, voteIncrement);
	}

}
