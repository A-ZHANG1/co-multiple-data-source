package trade.spring.data.neo4j.services;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.apiModel.SupplyChainStructure;
import trade.spring.data.neo4j.apiModel.graph.Link;
import trade.spring.data.neo4j.apiModel.graph.Node;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChain;
import trade.spring.data.neo4j.domain.node.SupplyChainNode;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;
import trade.spring.data.neo4j.domain.relationship.SupplyChainHasMember;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import trade.spring.data.neo4j.repositories.ContractRepository;
import trade.spring.data.neo4j.repositories.SupplyChainHasMemberRepository;
import trade.spring.data.neo4j.repositories.SupplyChainNodeRepository;
import trade.spring.data.neo4j.slpa.Community;
import trade.spring.data.neo4j.slpa.CommunityFinder;

import java.util.*;

/**
 * Created by Wayne.A.Z on 2019-04-29.
 */
@Service
public class SupplyChainService {

    @Autowired
    SupplyChainNodeRepository supplyChainRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    SupplyChainHasMemberRepository supplyChainHasMemberRepository;

//    public SupplyChain insertSupplyChain(SupplyChain sc, Set<Company> mems){
//        SupplyChain supplyChain = supplyChainRepository.save(sc);
//        if(supplyChain == null)
//            // save failed
//            return null;
//
//        // 每一个参与公司，加一条边
//        for(Company company : mems){
////            supplyChain.
//        }
//        return sc;
//    }

    /*
    企业类型             e.g.                       e.g.
    1：原材料制造     应用材料（中国)                应用材料（中国)
    2: 半导体        安森美半导体（上海）有限公司     灿芯半导体(上海)有限公司
    3：微电子制造     锐迪科微电子（上海）有限公司
    4: 配件制造      剑腾液晶显示(上海)有限公司       必达泰克光电设备(上海)有限公司
    5: 成品装配制造   爱博斯迪科化学(上海)有限公司     上海茂碧信息科技有限公司,上海薄荷信息科技有限公司
    */

    //type1: 1-2-3-4-5
    public SubGraph getSupplyChainType1(){
        SubGraph subGraph = new SubGraph();
        List<Map<String, Object>> map = companyRepository.getSupplyChainType1();
        for(Map m : map){
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("a")));
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("b")));
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("c")));
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("d")));
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("e")));

            subGraph.addLink(Link.buildFromContract((Contract) m.get("c1"), (ParticipateContract) m.get("r11"),
                    (ParticipateContract) m.get("r12")));
            subGraph.addLink(Link.buildFromContract((Contract) m.get("c2"), (ParticipateContract) m.get("r22"),
                    (ParticipateContract) m.get("r23")));
            subGraph.addLink(Link.buildFromContract((Contract) m.get("c3"), (ParticipateContract) m.get("r33"),
                    (ParticipateContract) m.get("r34")));
            subGraph.addLink(Link.buildFromContract((Contract) m.get("c4"), (ParticipateContract) m.get("r44"),
                    (ParticipateContract) m.get("r45")));

        }
        SupplyChain supplyChain = new SupplyChain(subGraph);
        System.out.println(supplyChain);
//        supplyChainRepository.insertSupplyChain(supplyChain);
//        supplyChainRepository.save(subGraph);
        return subGraph;
    }

    //type2: 1-2-4-5
    public SubGraph getSupplyChainType2(){
        SubGraph subGraph = new SubGraph();
        List<Map<String, Object>> map = companyRepository.getSupplyChainType2();

        for(Map m : map){
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("a")));
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("b")));
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("d")));
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("e")));

            subGraph.addLink(Link.buildFromContract((Contract) m.get("c1"), (ParticipateContract) m.get("r11"),
                    (ParticipateContract) m.get("r12")));
            subGraph.addLink(Link.buildFromContract((Contract) m.get("c2"), (ParticipateContract) m.get("r21"),
                    (ParticipateContract) m.get("r22")));
            subGraph.addLink(Link.buildFromContract((Contract) m.get("c3"), (ParticipateContract) m.get("r31"),
                    (ParticipateContract) m.get("r32")));
        }
//        System.out.println(subGraph);
        SupplyChain supplyChain = new SupplyChain(subGraph);
//        supplyChainRepository.insertSupplyChain(supplyChain);
//        supplyChainRepository.save(supplyChain);
        return subGraph;
    }

    public SupplyChainStructure calSupplyChains(double postProcessingThreshold) {
        // clear history data
        deleteAllSupplyChainNodes();

        SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        List<Contract> allContracts = (List<Contract>) contractRepository.findAll();
        for(Contract contract : allContracts) {
            List<Map<String, Company>> list = contractRepository.findContractParticipants(contract.getId());
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

        CommunityFinder cf = new CommunityFinder();
        cf.initIntegerNodeGraph(graph);
        cf.SLPA(20);
        cf.postProcessing(postProcessingThreshold);
        List<Community> communities = cf.getCommunities();


        // save
        SupplyChainStructure supplyChainStructure = new SupplyChainStructure();
        for(Community community : communities) {
            SupplyChainNode supplyChainNode = new SupplyChainNode();
            supplyChainNode.setName(String.valueOf(community.getId()));
            supplyChainNode.setMemberCounting(community.getMembers().size());
            supplyChainStructure.getSupplyChainNodes().add(supplyChainNode);

            supplyChainRepository.save(supplyChainNode);

            for(int companyId : community.getMembers().keySet()){
                Optional<Company> op = companyRepository.findById(Long.valueOf(companyId));
                if(!op.isPresent())
                    continue;
                Company company = op.get();

                SupplyChainHasMember hasMemberRelation = new SupplyChainHasMember();
                hasMemberRelation.setCompany(company);
                hasMemberRelation.setSupplyChain(supplyChainNode);
                hasMemberRelation.setConfidence(community.getMembers().get(companyId));
                supplyChainStructure.getSupplyChainHasMembers().add(hasMemberRelation);

                supplyChainHasMemberRepository.save(hasMemberRelation);
            }

        }

        return supplyChainStructure;
    }

    public boolean deleteAllSupplyChainNodes() {
        supplyChainRepository.deleteAllSupplyChainNodes();
        return true;
    }

}
