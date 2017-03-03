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
 * Domain class.
 *
 * @author Dilip S Sisodia
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
public class Domain implements Serializable {

  private static final long serialVersionUID = -93229233635186671L;

  @JsonIgnore
  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  @JacksonInject("restClient")
  protected RestClient restClient;

  private UUID id;
  private UUID account;
  private String name;

  public Domain(RestClient restClient) {
    this.restClient = restClient;
  }

  /**
   * create a domain or update domain by id.
   *
   * @return Domain class object
   * @throws IOException throws IOException if any error in connection to uri.
   */
  public Domain save() throws IOException {
    if (id == null) {
      return restClient.create("/domains", this, Domain.class);
    } else {
      return restClient.update("/domains/" + id.toString(), this);
    }
  }
}
