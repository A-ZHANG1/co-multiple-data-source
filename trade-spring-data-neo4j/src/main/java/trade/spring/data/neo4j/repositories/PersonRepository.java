package trade.spring.data.neo4j.repositories;

import trade.spring.data.neo4j.domain.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author pdtyreus
 * @author Mark Angrish
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    @Query("MATCH (m:Person) where m.name={name} RETURN m ")
    Person findByName(String name);

}