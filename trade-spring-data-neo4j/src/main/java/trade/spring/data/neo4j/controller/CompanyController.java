package trade.spring.data.neo4j.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import trade.spring.data.neo4j.services.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/all")
    public GeneralResponse findAllCompanies() {
        GeneralResponse<List<Company>> resp = new GeneralResponse<>();
        resp.setObj(companyService.findAllCompanies());
        return resp;
    }

    @PostMapping("/addOneCompany")
    public GeneralResponse addOneCompany(Company company){
        GeneralResponse<Company> resp = new GeneralResponse<>();
        resp.setObj(companyService.addCompany(company));
        return resp;
    }

    @GetMapping("/subgraphById")
    public GeneralResponse<SubGraph> getSubGraph(Long id, int depth){
        GeneralResponse<SubGraph> resp = new GeneralResponse<>();
        resp.setObj(companyService.getSubGraphById(id, depth));
        if(resp.getObj() == null)
            resp.setStatus(2);
        return resp;
    }

    @GetMapping("/subgraphByName")
    public GeneralResponse<SubGraph> getSubGraphByName(String companyName, int depth){
        GeneralResponse<SubGraph> resp = new GeneralResponse<>();
        resp.setObj(companyService.getSubGraphByCompanyName(companyName, depth));
        if(resp.getObj() == null)
            resp.setStatus(2);
        return resp;
    }

    @GetMapping("/test")
    public GeneralResponse test(Long id){
        GeneralResponse resp = new GeneralResponse<>();
        List list = companyRepository.test(1324L);
        System.out.println(list.size());
        resp.setObj(list);
        return resp;
    }

    @GetMapping("/info")
    public GeneralResponse getCompanyInfo(Long id) {
        GeneralResponse<Company> resp = new GeneralResponse<>();
        Company company = companyService.findById(id);
        if (company != null)
            resp.setObj(company);
        else
            resp.setStatus(2);
        return resp;
    }
}
