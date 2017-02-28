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
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
public class ExternalMailbox {

	@JacksonInject("restClient")
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	@JsonIgnore
	protected RestClient restClient;

	private UUID id;
	private UUID domain;
	private String local;
	private String relayHost;

	public ExternalMailbox(RestClient restClient) {
		this.restClient = restClient;
	}

	public ExternalMailbox save() throws IOException {
		if(id == null) {
			return restClient.create("external_mailboxes", this, ExternalMailbox.class);
		} else {
			return restClient.update("external_mailboxes/" + id.toString(), this, ExternalMailbox.class);
		}
	}
}