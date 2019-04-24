package trade.spring.data.neo4j.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import trade.spring.data.neo4j.domain.node.Company;
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

}
