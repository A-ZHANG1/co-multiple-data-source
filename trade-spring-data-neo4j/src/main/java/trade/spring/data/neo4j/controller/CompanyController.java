package trade.spring.data.neo4j.controller;

import java.util.List;
import trade.spring.data.neo4j.domain.Company;
import trade.spring.data.neo4j.services.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

@RestController
@RequestMapping("/")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/company")
    public List<Company> findAllCompanies() {
        return companyService.findAllCompanies();
    }
}
