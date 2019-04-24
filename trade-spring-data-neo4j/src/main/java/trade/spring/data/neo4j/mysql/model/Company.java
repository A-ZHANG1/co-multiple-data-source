package trade.spring.data.neo4j.mysql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Wayne.A.Z on 2019/1/19.
 */
@Getter
@Setter
@NoArgsConstructor
public class Company {
    private int companyId;

    private String companyName;

    private double capital;

    private double nodeWeight;

    private int core;

    private int type;

    public Company(String companyName, double capital, double nodeWeight) {
        this.companyName = companyName;
        this.capital = capital;
        this.nodeWeight = nodeWeight;
    }

    public Company(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (companyId != company.companyId) return false;
        if (Double.compare(company.capital, capital) != 0) return false;
        if (Double.compare(company.nodeWeight, nodeWeight) != 0) return false;
        if (core != company.core) return false;
        if (type != company.type) return false;
        return companyName != null ? companyName.equals(company.companyName) : company.companyName == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = companyId;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        temp = Double.doubleToLongBits(capital);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nodeWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + core;
        result = 31 * result + type;
        return result;
    }
}
