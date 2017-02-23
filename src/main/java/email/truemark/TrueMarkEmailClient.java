package email.truemark;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import email.truemark.http.RestClient;
import email.truemark.http.RestTemplateClient;
import email.truemark.model.Domain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Dilip S Sisodia
 */
public class TrueMarkEmailClient implements Serializable {

	private static final long serialVersionUID = 4296234100621890081L;
	protected String url;

	protected String getApiUrl(@Nonnull String path) {
		return !url.startsWith("http") ? this.url + path : path;
	}

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	public TrueMarkEmailClient(String url) {
		this.url = url;
		this.restClient = new RestTemplateClient(url);
	}

	public Domain getDomain(UUID id) throws IOException {
		return restClient.get(getApiUrl("domains/" + id.toString()), Domain.class);
	}

	public PagedView<Domain> getDomains() throws IOException {
		return get("domains", PagedView.class, Domain.class);
	}

	public Domain newDomain() {
		return new Domain(this.restClient);
	}

	public void deleteDomain(UUID id) throws IOException {
		delete("domains/" + id.toString());
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