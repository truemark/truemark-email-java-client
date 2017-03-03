package email.truemark;

import email.truemark.http.RestClient;
import email.truemark.http.URLConnectionRestClient;
import email.truemark.model.Alias;
import email.truemark.model.Domain;
import email.truemark.model.ExternalMailbox;
import email.truemark.model.MailBox;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import javax.annotation.Nonnull;

/*
 * @author Dilip S Sisodia
 */
public class TrueMarkEmailClient implements Serializable {

  private static final long serialVersionUID = 4296234100621890081L;
  private String url;
  private RestClient restClient;

  private String getApiUrl(@Nonnull String path) {
    return path.startsWith("http") ? path : this.url + path;
  }

  public TrueMarkEmailClient(String url) {
    this.url = url;
    this.restClient = new URLConnectionRestClient(url);
  }

  public Domain getDomain(UUID id) throws IOException {
    return get(getApiUrl("/domains/" + id.toString()), Domain.class);
  }

  @SuppressWarnings("unchecked")
  public PagedView<Domain> getDomains() throws IOException {
    return get("/domains", PagedView.class, Domain.class);
  }

  public Domain newDomain() {
    return new Domain(this.restClient);
  }

  public void deleteDomain(UUID id) throws IOException {
    delete("/domains/" + id.toString());
  }

  public MailBox getMailbox(UUID id) throws IOException {
    return get(getApiUrl("/mailboxes/" + id.toString()), MailBox.class);
  }

  @SuppressWarnings("unchecked")
  public PagedView<MailBox> getMailboxes() throws IOException {
    return get("/mailboxes", PagedView.class, MailBox.class);
  }

  public void deleteMailbox(UUID id) throws IOException {
    delete("/mailboxes/" + id.toString());
  }

  public MailBox newMailbox() {
    return new MailBox(this.restClient);
  }

  public Alias getAlias(UUID id) throws IOException {
    return get(getApiUrl("/aliases/" + id.toString()), Alias.class);
  }

  @SuppressWarnings("unchecked")
  public PagedView<Alias> getAliases() throws IOException {
    return get("/aliases", PagedView.class, Alias.class);
  }

  public void deleteAlias(UUID id) throws IOException {
    delete("/aliases/" + id.toString());
  }

  public Alias newAlias() {
    return new Alias(this.restClient);
  }

  @SuppressWarnings("unchecked")
  public PagedView<ExternalMailbox> getExternalMailboxes() throws IOException {
    return get("/external_mailboxes", PagedView.class, ExternalMailbox.class);
  }

  public ExternalMailbox getExternalMailbox(UUID id) throws IOException {
    return get("/external_mailboxes/" + id.toString(), ExternalMailbox.class);
  }

  public void deleteExternalMailbox(UUID id) throws IOException {
    delete("/external_mailboxes/" + id.toString());
  }

  public ExternalMailbox newExternalMailbox() {
    return new ExternalMailbox(this.restClient);
  }

  public <T> T get(@Nonnull String path, Class<T> clazz) throws IOException {
    return restClient.get(getApiUrl(path), clazz);
  }

  public <T, S> T get(String path, Class<T> clazz, Class<S> parameterClass) throws IOException {
    return restClient.get(getApiUrl(path), clazz, parameterClass);
  }

  public <T> void delete(String path) throws IOException {
    restClient.delete(getApiUrl(path));
  }
}
