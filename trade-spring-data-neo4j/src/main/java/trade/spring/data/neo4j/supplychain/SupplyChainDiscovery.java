package trade.spring.data.neo4j.supplychain;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import trade.spring.data.neo4j.apiModel.SupplyChainStructure;
import trade.spring.data.neo4j.common.Utils;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.SupplyChainNode;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.relationship.SupplyChainHasMember;
import trade.spring.data.neo4j.services.SupplyChainService;
import trade.spring.data.neo4j.supplychain.slpa.CacheUnit;
import trade.spring.data.neo4j.supplychain.slpa.Community;
import trade.spring.data.neo4j.supplychain.slpa.CommunityFinder;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by huangtao on 2019-05-13.
 */
public class SupplyChainDiscovery {

    // 新增贸易偏移现有供应链的次数，大于threshold则重新计算
    private int driftCounting = 0;

    // 上一次重新计算后的count
    private int counting = 0;

    // 抖动阈值
    private int shakeThreshold = 8;

    // 常规更新间隔
    private int periodicInterval = 100;

    // slpa postProcessing threshold
    private double slpaPostProcessingThreshold = 0.02;

    // cache
    private Queue<CacheUnit> cache = new ConcurrentLinkedQueue<>();

    private SupplyChainService supplyChainService;

    public SupplyChainDiscovery(SupplyChainService supplyChainService) {
        this.supplyChainService  = supplyChainService;
    }

    /**
     * 重新计算供应链结构
     * @return
     */
    private SupplyChainStructure calSupplyChains() {
        // clear history data
        deleteAllSupplyChainNodes();

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

        CommunityFinder cf = new CommunityFinder();
        cf.initIntegerNodeGraph(graph);
        cf.SLPA(20);
        cf.postProcessing(slpaPostProcessingThreshold);
        List<Community> communities = cf.getCommunities();


        // save
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

    private boolean deleteAllSupplyChainNodes() {
        supplyChainService.supplyChainRepository.deleteAllSupplyChainNodes();
        return true;
    }

    /**
     * 搜索多个企业共同的供应链
     * @param companies 多个企业
     * @return
     */
    private Set<Long> findCommonSupplyChainNodeIds(List<Company> companies) {
        Set<Long> supplyChainIds = new HashSet<>();

        if (companies.size() < 1) {
            return supplyChainIds;
        }

        // 用第一个公司初始化set
        List<SupplyChainNode> supplyChainNodes = supplyChainService.companyRepository.findSupplyChainNodesByCompanyId(companies.get(0).getId());
        for (SupplyChainNode node : supplyChainNodes) {
            supplyChainIds.add(node.getId());
        }

        // 循环求交集
        for (int i = 1; i < companies.size(); i++) {
            supplyChainNodes = supplyChainService.companyRepository.findSupplyChainNodesByCompanyId(companies.get(i).getId());
            Set<Long> tmpSet = new HashSet<>();
            for (SupplyChainNode node : supplyChainNodes) {
                if (supplyChainIds.contains(node.getId())) {
                    tmpSet.add(node.getId());
                }
            }
            supplyChainIds = tmpSet;

            if (supplyChainIds.size() == 0)
                break;
        }

        return supplyChainIds;
    }

    /**
     * 添加一个合同后，更新供应链结构
     * @param contract
     * @param companies 涉及的所有company
     */
    int count = 0;
    private void updateSupplyChainNodes(Contract contract, List<Company> companies) {
        count++;
        counting++;
        Set<Long> commonSupplyChainNodeIds = findCommonSupplyChainNodeIds(companies);
        if (commonSupplyChainNodeIds.size() > 0) {
            // 循环更新所有涉及的供应链节点
            for (Long id : commonSupplyChainNodeIds) {
                Optional<SupplyChainNode> op = supplyChainService.supplyChainRepository.findById(id);
                if (!op.isPresent())
                    continue;

                SupplyChainNode node = op.get();
                node.setLastUpdateTime(Utils.getTimeStamp());
                supplyChainService.supplyChainRepository.save(node);
            }
        } else {
            driftCounting++;
            System.out.println(count + " " + counting + " supply chain drift counting: " + driftCounting);
        }

        if(driftCounting >= shakeThreshold) {
            counting = 0;
            driftCounting = 0;
            shakeThreshold *= 2;

            System.out.println("start cleaning and recomputing...");
            calSupplyChains();
            System.out.println("finish cleaning and recomputing!");
        } else if(counting >= periodicInterval) {
            counting = 0;
            driftCounting = 0;
            shakeThreshold /= 2;

            System.out.println("start regular update...");
            calSupplyChains();
            System.out.println("finish regular update!");
        }

    }

    private Runnable runnable = () -> {
        while (!cache.isEmpty()) {
            // System.out.println(cache.size());
            CacheUnit cacheUnit = cache.poll();
            if (cacheUnit != null) {
                updateSupplyChainNodes(cacheUnit.getContract(), cacheUnit.getCompanies());
            }
        }
    };

    private Thread asynThread = new Thread(runnable);

    // discovery的起点
    public void addCache(CacheUnit cacheUnit) {
        cache.add(cacheUnit);
        if(!asynThread.isAlive()) {
            asynThread = new Thread(runnable);
            asynThread.start();
        }
    }
}
