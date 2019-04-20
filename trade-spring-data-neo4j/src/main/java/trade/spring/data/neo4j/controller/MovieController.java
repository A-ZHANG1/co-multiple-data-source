package trade.spring.data.neo4j.controller;

import java.util.List;
import java.util.Map;

import trade.spring.data.neo4j.domain.Movie;
import trade.spring.data.neo4j.domain.Person;
import trade.spring.data.neo4j.services.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@RestController
@RequestMapping("/")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return movieService.graph(limit == null ? 100 : limit);
	}

	@GetMapping("/movies")
	public List<Movie> findAllMovies() {
		List<Movie> result = movieService.findAll();
		return result;
	}

	@GetMapping("/person")
	public Person findByName(@RequestParam(value = "name",required = false) String name) {
		Person result = movieService.findByName(name);
		return result;
	}


}
