package trade.spring.data.neo4j.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import trade.spring.data.neo4j.apiModel.graph.Link;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

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

    public SubGraph getSubGraph(Long id, int k){
        Set<Company> visited = new HashSet<>();
        Set<Link> links = new HashSet<>();
        List<Company> bfsQueue = new ArrayList<>();

        Optional<Company> op = companyRepository.findById(id);
        if(!op.isPresent())
            return null;

        Company startPoint = op.get();
        bfsQueue.add(startPoint);
        visited.add(startPoint);

        while(k-- > 0){
            List<Company> tmpQueue = new ArrayList<Company>();
            for(Company company : bfsQueue){
                SubGraph subGraph = findNeighborGraphById(company.getId());
                links.addAll(subGraph.getLinks());
                for(Company c : subGraph.getNodes()){
                    if(!visited.contains(c)){
                        visited.add(c);
                        tmpQueue.add(c);
                    }
                }
            }
            bfsQueue = tmpQueue;
        }

        SubGraph graph = new SubGraph();
        graph.setNodes(visited);
        graph.setLinks(links);
        return graph;
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
        List<Map<String, Object>> map = companyRepository.findNeighborById(id);
        for(Map m : map){
            subGraph.getNodes().add((Company) m.get("c"));
            subGraph.getLinks().add(Link.buildFromContract((Contract) m.get("b"), (ParticipateContract) m.get("r1"),
                    (ParticipateContract) m.get("r2")));
        }
        return subGraph;
    }

    public Company findById(Long id){
        return companyRepository.findById(id).get();
    }
}
