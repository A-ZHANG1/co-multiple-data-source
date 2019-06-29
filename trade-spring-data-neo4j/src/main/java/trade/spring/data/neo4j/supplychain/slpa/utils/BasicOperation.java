package trade.spring.data.neo4j.supplychain.slpa.utils;


import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import trade.spring.data.neo4j.apiModel.SupplyChainStructure;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChainNode;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.relationship.SupplyChainHasMember;
import trade.spring.data.neo4j.services.SupplyChainService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//method1.转换到企业链接图维度
//method2.SupplyChainStructure写入repository
public class  BasicOperation {

    @Autowired
    SupplyChainService supplyChainService;


    //IntegerNode(company)--Double(contract amount)--IntegerNode(company) 形式的维度投影图
    //合同组合成node-activity
    //维度转换
    public SimpleDirectedWeightedGraph getGraphCompanyDim(){
        SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        List<Contract> allContracts = (List<Contract>) supplyChainService.contractRepository.findAll();
        for(Contract contract : allContracts) {
            List<Map<String, Company>> list = supplyChainService.contractRepository.findContractParticipants(contract.getId());
            Company a = list.get(0).get("a");
            Company c = list.get(0).get("c");

            int aId = Math.toIntExact(a.getId());
            int cId = Math.toIntExact(c.getId());

            graph.addVertex(aId);
            graph.addVertex(cId);

            // 边不需要重复，且需要赋权
            if(graph.containsEdge(aId, cId)){
                DefaultWeightedEdge edge = graph.getEdge(aId, cId);
                graph.setEdgeWeight(edge, graph.getEdgeWeight(edge) + contract.getAmount());

                edge = graph.getEdge(cId, aId);
                graph.setEdgeWeight(edge, graph.getEdgeWeight(edge) + contract.getAmount());
            } else {
                graph.addEdge(aId, cId);
                graph.setEdgeWeight(graph.getEdge(aId, cId), contract.getAmount());

                graph.addEdge(cId, aId);
                graph.setEdgeWeight(graph.getEdge(cId, aId), contract.getAmount());
            }
        }
        return graph;
    }

    //将计算所得供应链结构写入repository
    public SupplyChainStructure SaveSupplyChainStructureToRepository(List<Community> communities){
        SupplyChainStructure supplyChainStructure = new SupplyChainStructure();
        for(Community community : communities) {
            SupplyChainNode supplyChainNode = new SupplyChainNode();
            supplyChainNode.setName(String.valueOf(community.getId()));
            supplyChainNode.setMemberCounting(community.getMembers().size());
            supplyChainStructure.getSupplyChainNodes().add(supplyChainNode);

            supplyChainService.supplyChainRepository.save(supplyChainNode);

            for(int companyId : community.getMembers().keySet()){
                Optional<Company> op = supplyChainService.companyRepository.findById(Long.valueOf(companyId));
                if(!op.isPresent())
                    continue;
                Company company = op.get();

                SupplyChainHasMember hasMemberRelation = new SupplyChainHasMember();
                hasMemberRelation.setCompany(company);
                hasMemberRelation.setSupplyChain(supplyChainNode);
                hasMemberRelation.setConfidence(community.getMembers().get(companyId));
                supplyChainStructure.getSupplyChainHasMembers().add(hasMemberRelation);

                supplyChainService.supplyChainHasMemberRepository.save(hasMemberRelation);
            }

        }
        return supplyChainStructure;
    }
}
