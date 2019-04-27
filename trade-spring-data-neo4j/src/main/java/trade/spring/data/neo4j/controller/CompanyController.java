package trade.spring.data.neo4j.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.annotations.ApiOperation;
import org.neo4j.ogm.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.apiModel.graph.SubGraph;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import trade.spring.data.neo4j.services.CompanyService;

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
    @ResponseBody
    @ApiOperation("返回所有公司节点")
    public GeneralResponse findAllCompanies() {
        GeneralResponse<List<Company>> resp = new GeneralResponse<>();
        resp.setObj(companyService.findAllCompanies());
        return resp;
    }

    @PostMapping("/addOneCompany")
    @ResponseBody
    @ApiOperation("新建公司节点")
    public GeneralResponse addOneCompany(Company company){
        GeneralResponse<Company> resp = new GeneralResponse<>();
        resp.setObj(companyService.addCompany(company));
        return resp;
    }

//    @GetMapping("/neighbor")
//    public GeneralResponse<Set<Company>> getCompanyNeighbor(Long id, int depth){
//        GeneralResponse<Set<Company>> resp = new GeneralResponse<>();
//        resp.setObj(companyService.getSubGraph(id, depth));
//        if(resp.getObj() == null)
//            resp.setStatus(2);
//        return resp;
//    }

    @GetMapping("/subgraph")
    @ResponseBody
    @ApiOperation("返回n-hop子图")
    public GeneralResponse<SubGraph> getSubGraph(@RequestParam Long id,@RequestParam int depth){
        GeneralResponse<SubGraph> resp = new GeneralResponse<>();
        resp.setObj(companyService.getSubGraph(id, depth));
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

    @GetMapping("/setCompanyType")
    public GeneralResponse setCompanyType(String companyName, int type){
        Company c = companyRepository.setCompanyType(companyName, type);
        GeneralResponse<Company> resp = new GeneralResponse<>();
        resp.setObj(c);
        return resp;
    }

    @GetMapping("/getSupplyChain")
    public GeneralResponse getSupplyChain(){
        List<Map<String,Object>> supplyChain = companyRepository.getSupplyChain();
        GeneralResponse<List<Map<String,Object>>> resp = new GeneralResponse<>();
        resp.setObj(supplyChain);
        return resp;
    }
}
