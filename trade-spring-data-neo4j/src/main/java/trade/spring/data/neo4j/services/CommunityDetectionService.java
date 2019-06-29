package trade.spring.data.neo4j.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.domain.node.Company;

import java.util.*;


@Service
public class CommunityDetectionService {
    @Autowired
    private BMLPACommunityFinderService bmlpaCommunityFinderService;

    public GeneralResponse<List<Company>> executeBMLPA() {

        List<Company> groupedCompanies = bmlpaCommunityFinderService.executeBMLPA();

        GeneralResponse resp = new GeneralResponse();
        resp.setObj(groupedCompanies);
        return resp;
    }


}
