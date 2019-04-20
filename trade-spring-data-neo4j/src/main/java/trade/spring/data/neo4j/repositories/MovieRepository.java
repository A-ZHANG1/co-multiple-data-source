package trade.spring.data.neo4j.repositories;

import java.util.Collection;
import java.util.List;

import trade.spring.data.neo4j.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 * @author Michael J. Simons
 */

//将spring data repository自动输出为rest资源
//collectionResourceRel： rel value to use when generating links to the collection resource.
	//The path segment under which this resource is to be exported.
//@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

	Movie findByTitle(@Param("title") String title);

	Collection<Movie> findByTitleLike(@Param("title") String title);

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
	Collection<Movie> graph(@Param("limit") int limit);

	@Query("MATCH (m:Movie) RETURN m")
	List<Movie> findAll();
}