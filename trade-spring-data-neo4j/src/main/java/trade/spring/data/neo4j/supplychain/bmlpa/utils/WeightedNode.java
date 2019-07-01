package trade.spring.data.neo4j.supplychain.bmlpa.utils;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashMap;

@ApiModel
@Getter
@Setter
@NoArgsConstructor

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

	public void updateCommunityDistribution(int votedCommunity, double voteIncrement) {
		if (communityDistribution.containsKey(votedCommunity))
			voteIncrement += communityDistribution.get(votedCommunity);
		communityDistribution .put(votedCommunity, voteIncrement);
	}
}
