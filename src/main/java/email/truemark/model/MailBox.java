package email.truemark.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import email.truemark.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Dilip S Sisodia
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
@Accessors(chain = true)
public class MailBox implements Serializable {
	private static final long serialVersionUID = 5913269129622026522L;

	@JsonIgnore
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	@JacksonInject("restClient")
	protected RestClient restClient;

	private UUID id;
	private String local;
	private String password;
	private UUID domain;
	private boolean enabled;
	private String hostIp;
	private String hostPassword;

	public MailBox(RestClient restClient) {
		this.restClient = restClient;
	}

	public MailBox save() throws IOException {
		if(id == null) {
			return restClient.create("/mailboxes", this, MailBox.class);
		} else {
			return restClient.update("/mailboxes/" + id.toString(), this, MailBox.class);
		}
	}
}