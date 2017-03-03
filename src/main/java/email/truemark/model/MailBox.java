package email.truemark.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import email.truemark.http.RestClient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Mailbox class.
 *
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

  /**
   * method to save of update a mailbox object.
   *
   * @return Mailbox class object
   * @throws IOException throws IOException if connection fails.
   */
  public MailBox save() throws IOException {
    if (id == null) {
      return restClient.create("/mailboxes", this, MailBox.class);
    } else {
      return restClient.update("/mailboxes/" + id.toString(), this);
    }
  }
}
