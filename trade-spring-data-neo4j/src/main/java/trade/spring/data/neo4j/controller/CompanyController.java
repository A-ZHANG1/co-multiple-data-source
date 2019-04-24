package trade.spring.data.neo4j.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
}
