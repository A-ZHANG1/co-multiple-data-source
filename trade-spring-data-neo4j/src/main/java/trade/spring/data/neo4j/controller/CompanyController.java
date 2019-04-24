package trade.spring.data.neo4j.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.domain.node.Company;
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

    @GetMapping("/all")
    public List<Company> findAllCompanies() {
        return companyService.findAllCompanies();
    }

    @PostMapping("/addOneCompany")
    public Company addOneCompany(Company company){
        return companyService.addCompany(company);
    }

    @GetMapping("/neighbor")
    public GeneralResponse<Set<Company>> getCompanyNeighbor(String companyName, int depth){
        GeneralResponse<Set<Company>> resp = new GeneralResponse<>();
        resp.setObj(companyService.getSubGraph(companyName, depth));
        return resp;
    }

    @GetMapping("/test")
    public GeneralResponse test(String companyName, int depth){
        GeneralResponse resp = new GeneralResponse<>();

        Set<Company> companies = companyService.getSubGraph(companyName, depth);
        System.out.println(companies.size());

        Set<String> strings = new HashSet<>();
        for(Company company : companies){
            strings.add(company.getCompanyName());
        }
        System.out.println(strings.size());
        return resp;
    }
}
