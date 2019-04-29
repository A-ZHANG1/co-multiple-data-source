package trade.spring.data.neo4j.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import trade.spring.data.neo4j.apiModel.graph.Link;
import trade.spring.data.neo4j.apiModel.graph.Node;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChain;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.spring.data.neo4j.repositories.SupplyChainRepository;


/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SupplyChainRepository supplyChainRepository;

    @Transactional(readOnly = true)
    public List<Company> findAllCompanies() {
        List<Company> result = companyRepository.findAll();
        return result;
    }

    public Company addCompany(String companyName){
        Company company = new Company();
        company.setCompanyName(companyName);
        return companyRepository.save(company);
    }

    public Company addCompany(Company company){
        return companyRepository.save(company);
    }

    private SubGraph getSubGraph(Company startPoint, int k){
        // 已访问过的公司索引
        Map<String, Node> nodeMap = new HashMap<>();
        // 每次遍历的queue
        List<Node> bfsQueue = new ArrayList<>();
        // 用于快速检索已有的link
        Map<String, Set<String>> linkMap = new HashMap<>();

        // 结果集
        Set<Node> nodes = new HashSet<>();
        Set<Link> links = new HashSet<>();

        // 初始化起点node
        Node startNode = Node.buildFromCompany(startPoint);
        startNode.setDistance(0);

        // 初始化遍历环境
        bfsQueue.add(startNode);
        nodes.add(startNode);
        nodeMap.put(startNode.getCompanyName(), startNode);

        for (int i = 1; i <= k; i++) {
            List<Node> tmpQueue = new ArrayList<>();
            for (Node node : bfsQueue) {
                SubGraph subGraph = findNeighborGraphById(node.getId());

                for (Node n : subGraph.getNodes()) {
                    if (!nodeMap.containsKey(n.getCompanyName())) {
                        n.setDistance(i);
                        nodeMap.put(n.getCompanyName(), n);
                        tmpQueue.add(n);
                        nodes.add(n);
                    }
                }

                for(Link link : subGraph.getLinks()){
                    if(linkMap.get(link.getPartyAName()) == null || !linkMap.get(link.getPartyAName()).contains(link.getPartyBName())){
                        // not existed
                        links.add(link);
                        if(linkMap.get(link.getPartyAName()) == null){
                            linkMap.put(link.getPartyAName(), new HashSet<>());
                        }
                        if(linkMap.get(link.getPartyBName()) == null){
                            linkMap.put(link.getPartyBName(), new HashSet<>());
                        }
                        linkMap.get(link.getPartyAName()).add(link.getPartyBName());
                        linkMap.get(link.getPartyBName()).add(link.getPartyAName());

                        // 修改node权重，虽然下面两个if比较多余
                        if(nodeMap.containsKey(link.getPartyAName())){
                            nodeMap.get(link.getPartyAName()).increment(link.getLinkWeight());
                        }
                        if(nodeMap.containsKey(link.getPartyBName())){
                            nodeMap.get(link.getPartyBName()).increment(link.getLinkWeight());
                        }
                    }
                }


            }
            bfsQueue = tmpQueue;
        }

        SubGraph graph = new SubGraph();
        graph.setNodes(nodes);
        graph.setLinks(links);
        return graph;
    }

    public SubGraph getSubGraphById(Long id, int k){
        Optional<Company> op = companyRepository.findById(id);
        if(!op.isPresent())
            return null;

        return getSubGraph(op.get(), k);
    }

    public SubGraph getSubGraphByCompanyName(String companyName, int k){
        Company startPoint = companyRepository.findByCompanyName(companyName);
        if(startPoint == null)
            return null;

        return getSubGraph(startPoint, k);
    }

    public Set<Company> findNeighborCompanyByName(Company company){
        Set<Company> results = new HashSet<>();
        results.addAll(companyRepository.findNeighborByCompanyName(company.getCompanyName()));
        return results;
    }

    public Set<Company> findNeighborCompanyById(Long id){
        Set<Company> results = new HashSet<>();
        List<Map<String, Object>> map = companyRepository.findNeighborById(id);
        for(Map m : map){
            results.add((Company)m.get("c"));
        }
        return results;
    }

    public SubGraph findNeighborGraphById(Long id){
        SubGraph subGraph = new SubGraph();
        Map<String, Map<String, Link>> linkMap = new HashMap<>();

        List<Map<String, Object>> map = companyRepository.findNeighborById(id);
        for(Map m : map){
            subGraph.getNodes().add(Node.buildFromCompany((Company) m.get("c")));

            // check link duplication
            Link link = Link.buildFromContract((Contract) m.get("b"), (ParticipateContract) m.get("r1"), (ParticipateContract) m.get("r2"));
            if(linkMap.get(link.getPartyAName()) != null && linkMap.get(link.getPartyAName()).get(link.getPartyBName()) != null){
                // existed
                linkMap.get(link.getPartyAName()).get(link.getPartyBName()).weightIncrement();
            } else {
                subGraph.getLinks().add(link);
                if(linkMap.get(link.getPartyAName()) == null){
                    linkMap.put(link.getPartyAName(), new HashMap<>());
                }
                if(linkMap.get(link.getPartyBName()) == null){
                    linkMap.put(link.getPartyBName(), new HashMap<>());
                }
                linkMap.get(link.getPartyAName()).put(link.getPartyBName(), link);
                linkMap.get(link.getPartyBName()).put(link.getPartyAName(), link);
            }
        }
        return subGraph;
    }

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
        supplyChainRepository.insertSupplyChain(supplyChain);
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
        supplyChainRepository.save(supplyChain);

        return subGraph;
    }

    public Company findById(Long id){
        return companyRepository.findById(id).get();
    }
}
