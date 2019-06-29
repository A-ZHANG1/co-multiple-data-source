package trade.spring.data.neo4j.services;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import trade.spring.data.neo4j.supplychain.bmlpa.utils.NodeWeightedGraph;
import trade.spring.data.neo4j.supplychain.bmlpa.utils.WeightedNode;
import trade.spring.data.neo4j.supplychain.slpa.utils.BasicOperation;
import trade.spring.data.neo4j.supplychain.slpa.utils.IntegerNode;
import trade.spring.data.neo4j.supplychain.slpa.utils.IntegerNodeGraph;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BMLPACommunityFinderService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CompanyService companyService;

	Map<Company, List<Double>> initialCommunity = new HashMap<>();

	NodeWeightedGraph wGraph = new NodeWeightedGraph();

//	public void initNodeWeightedGraph(SimpleDirectedWeightedGraph graph) {
//		double capital;
//		wGraph.setGraph(graph);
//		for(int nodeId : wGraph.getGraph().vertexSet()){
//			capital = companyService.getCapitalById(Long.valueOf(nodeId));
//			WeightedNode node = new WeightedNode(nodeId,capital);
//			wGraph.getNodeMap().add(node);
//		}
//	}
	public NodeWeightedGraph initNodeWeightedGraph(SimpleDirectedWeightedGraph graph) {
		double capital;
		wGraph.setGraph(graph);
		for(int nodeId : wGraph.getGraph().vertexSet()){
			capital = companyService.getCapitalById(Long.valueOf(nodeId));
			WeightedNode node = new WeightedNode(nodeId,capital);
			wGraph.getNodeMap().add(node);
		}
		return wGraph;
	}

	//低风险BMLPA，按照节点注册资本初始化core
	public GeneralResponse<Map<Company, List<Double>>> roughCoreByCapital() {

		//todo: k -core k参数可设定。 但是没有验证数据所以必要性存疑

		//这里可以统一neo4j和mysql到统一接口
		List<Company> companies = companyService.findAllCompanies();
		companies.sort(Comparator.comparing(Company::getCapital));

		double label = 1;

		for (Company c : companies) {

			List<Double> labelList = new ArrayList<>();
			//若尚未打社群标签
			if (!initialCommunity.containsKey(c)) {
				labelList.add(label);
				labelList.add(1.0);//初始化belonging_coefficient归属因子为1
				initialCommunity.put(c, labelList);

				//邻接最大注册资本节点加入core
				Set<Company> nbs = companyService.findNeighborCompanyById(c.getId());
				List<Company> neighbors = new ArrayList<>();
				neighbors.addAll(nbs);
				neighbors.sort(Comparator.comparing(Company::getCapital));
				Company maxNb = neighbors.get(0);
				initialCommunity.put(maxNb, labelList);

				//共有邻居中最大注册资本入core
				Set<Company> nbnbs = companyService.findNeighborCompanyById(maxNb.getId());
				List<Company> nbNeighbors = new ArrayList<>();
				neighbors.addAll(nbnbs);

				for (Company commonNb : neighbors) {
					if (nbNeighbors.indexOf(commonNb) != -1) {
						initialCommunity.put(commonNb, labelList);
						break;
					}
				}
				label++;
			}
		}

		GeneralResponse<Map<Company, List<Double>>> resp = new GeneralResponse<>();
		resp.setObj(initialCommunity);
		return resp;
	}

	//  public GeneralResponse<Map<Company, Map<Double,Double>>> propagation(int threshold,int iter){
	public GeneralResponse<Map<Company, Map<Double, Double>>> propagation(double threshold) {

		roughCoreByCapital();

		double communityId, belongingCoeff;
		Map<Company, Map<Double, Double>> nbPropagation = new HashMap<>();

		int converge = 0;
		int iter = 0;

		while (converge != companyService.findAllCompanies().size()) {
			iter++;

			//一次迭代 ，更新所有节点
			for (Company c : companyService.findAllCompanies()) {

				Map<Double, Double> nodeCommunityMap = new HashMap<>(); //iter i不考虑当前节点iter i-1被打的标签

				//邻接节点(community_label,belonging_coefficient)求和
				for (Company nb : companyService.findNeighborCompanyById(c.getId())) {
					communityId = initialCommunity.get(nb).get(0);
					belongingCoeff = initialCommunity.get(nb).get(1);

					//加边权重

					if (nodeCommunityMap.containsKey(communityId)) {
						nodeCommunityMap.replace(communityId, nodeCommunityMap.get(communityId) + belongingCoeff);
					} else {
						nodeCommunityMap.put(communityId, belongingCoeff);
					}
				}

				//最大的belonging_coefficient
				double maxB = nodeCommunityMap.entrySet().stream().
						max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();

				//除以最大值
				Map<Double, Double> normalizedNodeCommunityMap = nodeCommunityMap.entrySet().stream()
						.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue() / maxB));

				//剔除过小的b。（可能造成所有c都被剔除）
				Map<Double, Double> filteredNodeCommunityMap = (Map<Double, Double>) normalizedNodeCommunityMap.entrySet().stream()
						.filter(e -> e.getValue() < threshold).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

				//剩余b和调整为1
				double sumB = filteredNodeCommunityMap.entrySet().stream().mapToDouble(e -> e.getValue()).sum();
				Map<Double, Double> propagatedNodeCommunityMap = filteredNodeCommunityMap.entrySet().stream()
						.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue() / sumB));

				if (converge == 0) {
					nbPropagation.put(c, propagatedNodeCommunityMap);
				}

				//若每个节点标签集不变（converge==201,公司节点数），退出循环
				if (nbPropagation.get(c) == nodeCommunityMap) {
					converge++;
				} else {
					nbPropagation.put(c, propagatedNodeCommunityMap);
				}
			}
			System.out.println("community amount: " + nbPropagation.size() + " in iter "+ iter + ". node converging this iter: " + converge);

		}

		GeneralResponse<Map<Company, Map<Double, Double>>> resp = new GeneralResponse<>();
		resp.setObj(nbPropagation);
		return resp;
	}

//		public GeneralResponse<List<Company>> executeBMLPA(int type, double threshold) {
		public List<Company> executeBMLPA() {

			// clear history data
//			deleteAllSupplyChainNodes();

			BasicOperation basicOperation = new BasicOperation();
			SimpleDirectedWeightedGraph graph = basicOperation.getGraphCompanyDim();

			initNodeWeightedGraph(graph);
		List<Company> groupedCompanies = new ArrayList<>();
//		switch (type) {
		switch (1) {
			case 0:
//        roughCore();break;
			case 1:
				roughCoreByCapital();
				break;
			case 2:
//        roughCoreByContractAmount();
				break;
		}
		return groupedCompanies;
	}

	public List<Company> findAll(){
//		companyRepository.findNeighborById(Long.valueOf(1071));
//		companyRepository.findSupplyChainNodesByCompanyId(Long.valueOf(1071));

		return companyService.findAllCompanies();
	}

	public static void main(String arg[]) {
		BMLPACommunityFinderService b = new BMLPACommunityFinderService();
		List<Company> companies = b.findAll();

		System.out.println(companies);
	}
}
