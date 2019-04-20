package trade.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mark Angrish
 */
@lombok.Data
//@NoArgsConstructor
@NodeEntity
public class Movie {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private int released;
	private String tagline;

	public Movie(String title, int released, String tagline) {
		this.title = title;
		this.released = released;
		this.tagline = tagline;
	}

	@JsonIgnoreProperties("movie")
	@Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
	private List<Role> roles;

	public void addRole(Role role) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		this.roles.add(role);
	}

	public String getTitle() {
		return title;
	}
}