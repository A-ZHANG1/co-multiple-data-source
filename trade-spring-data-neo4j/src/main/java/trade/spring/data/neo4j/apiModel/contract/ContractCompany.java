package trade.spring.data.neo4j.apiModel.contract;

import trade.spring.data.neo4j.domain.node.contract.Role;

/**
 * 合同中参与方结构
 *
 * Created by huangtao on 2019-04-22.
 */
public class ContractCompany {

    private String id;

    private String name;

    private Role role;

    public ContractCompany(){}

    public ContractCompany(String id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ContractCompany{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", role=" + role + '}';
    }
}
