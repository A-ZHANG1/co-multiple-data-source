package trade.spring.data.neo4j.apiModel.graph;

import trade.spring.data.neo4j.domain.node.Company;

/**
 * Created by huangtao on 2019-04-26.
 */

@lombok.Data
public class Node {

    private Long id;

    private Long companyId;

    private String companyName;

    private double capital = 0;

    private double nodeWeight;

    private int core;

    private int type;

    // 与中心点的最短距离
    private int distance;

    public static Node buildFromCompany(Company company) {
        Node node = new Node();
        node.setId(company.getId());
        node.setCompanyId(company.getCompanyId());
        node.setCompanyName(company.getCompanyName());
        // node.setCapital(company.getCapital());
        node.setNodeWeight(company.getNodeWeight());
        node.setCore(company.getCore());
        node.setType(company.getType());
        return node;
    }

    public void increment(double k) {
        capital += k;
    }

}
