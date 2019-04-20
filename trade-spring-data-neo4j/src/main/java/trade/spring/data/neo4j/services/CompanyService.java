package trade.spring.data.neo4j.services;

import java.util.*;

import trade.spring.data.neo4j.domain.Company;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Wayne.A.Z on 2019/4/18.
 */

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public List<Company> findAllCompanies() {
        List<Company> result = companyRepository.findAll();
        return result;
    }

}
