package trade.spring.data.neo4j.apiModel.graph;

import trade.spring.data.neo4j.domain.node.contract.Contract;
import trade.spring.data.neo4j.domain.node.contract.Role;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;

import java.util.Objects;

/**
 * Created by huangtao on 2019-04-25.
 */

@lombok.Data
public class Link {

    private long linkId;

    private String partyAName;

    private String partyBName;

    private double linkWeight;

    public static Link buildFromContract(Contract contract, ParticipateContract r1, ParticipateContract r2) {
        Link link = new Link();
        link.linkId = contract.getId();

        if(r1.getRole() == Role.PartyA){
            link.partyAName = r1.getCompany().getCompanyName();
            link.partyBName = r2.getCompany().getCompanyName();
        } else if(r1.getRole() == Role.PartyB) {
            link.partyBName = r1.getCompany().getCompanyName();
            link.partyAName = r2.getCompany().getCompanyName();
        }

        link.linkWeight = contract.getAmount();
//        link.linkWeight = 1;
        return link;
    }

    public void weightIncrement() {
        linkWeight++;
    }

    public void weightIncrement(double weight) {
        linkWeight += weight;
    }

    public static void main(String[] args) {
        Link link1 = new Link();
        Link link2 = new Link();

        System.out.println(link1.equals(link2));
    }

    //SubGraph新加入边去重（set.add/addAll）需要重写equals方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(partyAName, link.partyAName) &&
                Objects.equals(partyBName, link.partyBName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyAName, partyBName);
    }
}
