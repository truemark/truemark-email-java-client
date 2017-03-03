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
import java.util.UUID;

/**
 * Alias class.
 *
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

  /**
   * method to save or update an alias.
   *
   * @return Alias object
   * @throws IOException throws IOException if connection fails.
   */
  public Alias save() throws IOException {
    if (id == null) {
      return restClient.create("/aliases", this);
    } else {
      return restClient.update("/aliases/" + id.toString(), this);
    }
  }
}
