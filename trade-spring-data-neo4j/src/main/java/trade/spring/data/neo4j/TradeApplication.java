package trade.spring.data.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 */

@SpringBootApplication
@EnableNeo4jRepositories

public class TradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }
}