package email.truemark.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import email.truemark.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Dilip S Sisodia
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
@Accessors(chain = true)
public class Alias {

	@JacksonInject("restClient")
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	@JsonIgnore
	protected RestClient restClient;

	private UUID id;
	private UUID domain;
	private String local;
	private String recipient;

	public Alias(RestClient restClient) {
		this.restClient = restClient;
	}

	public Alias save() throws IOException {
		if(id == null) {
			return restClient.create("/aliases", this, Alias.class);
		} else {
			return restClient.update("/aliases/" + id.toString(), this, Alias.class);
		}
	}
}