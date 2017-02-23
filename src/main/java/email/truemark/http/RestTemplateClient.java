package email.truemark.http;

import com.fasterxml.jackson.databind.*;
import email.truemark.PagedView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author Dilip S Sisodia
 */
@Slf4j
public class RestTemplateClient implements RestClient {
	private static final long serialVersionUID = -9011264090560650316L;

	protected HttpHeaders headers;
	protected RestTemplate restTemplate;
	protected String uri;
	protected ObjectMapper objectMapper;
	protected InjectableValues injectableValues;

	public RestTemplateClient(String uri) {
		this.restTemplate = new RestTemplate();
		this.restTemplate.setErrorHandler(new RestErrorHandler());
		this.uri = uri;
		this.objectMapper = new ObjectMapper();
		if (log.isDebugEnabled()) {
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
		injectableValues = new InjectableValues.Std()
				.addValue("restClient", this);
	}

	protected String getApiUrl(@Nonnull String path) {
		return !path.startsWith("http") ? (this.uri + (path.startsWith("/") ? path : ("/" + path))) : path;
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		return headers;
	}

	private HttpHeaders getHttpPostHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	/**
	 * Sends an HTTP GET request and loads the response into the provided object instance.
	 *
	 * @param uri      the URI to GET
	 * @param instance the instance to load the response into
	 * @return the instance passed in for convenience
	 * @throws IOException if an error occurs
	 */
	@Override
	public <T> T get(String uri, T instance) throws IOException {
		//TODO implement as required
		return null;
	}

	/**
	 * Sends an HTTP GET request and loads the response into a new instance of the provided type.
	 *
	 * @param uri   the URI to GET
	 * @param clazz the type of instance to create
	 * @return the newly created instance
	 * @throws IOException if an error occurs
	 */
	@Override
	public <T> T get(String uri, Class<T> clazz) throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, getHttpHeaders());
			ResponseEntity<String> response = restTemplate.exchange(getApiUrl(uri), HttpMethod.GET, request, String.class);
			return objectMapper.reader().with(injectableValues).forType(clazz).readValue(response.getBody().toString());
	}

	/**
	 * Sends an HTTP GET request and loads the response into a new instance of the provided type. This
	 * method is specifically used to load instances of object which are using generics. To get around
	 * the type erasure problem, the parameterClass is used to specify the type parameter to use for the
	 * generic class.
	 *
	 * @param uri            the URI to GET
	 * @param clazz          the type of instance to create
	 * @param parameterClass the type parameter class
	 * @return the newly created instance
	 * @throws IOException if an error occurs
	 */
	@Override
	public <T, S> T get(String uri, Class<T> clazz, Class<S> parameterClass) throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, getHttpHeaders());
		log.debug(getApiUrl(uri));
		ResponseEntity<String> response = restTemplate.exchange(getApiUrl(uri), HttpMethod.GET, request, String.class);
		log.debug(response.toString());
		JavaType type = objectMapper.getTypeFactory().constructParametricType(PagedView.class, parameterClass);
		return objectMapper.reader().with(injectableValues).forType(type).readValue(response.getBody());
	}

	/**
	 * Sends an HTTP PUT request to issue an update. The response is loaded back into the given object.
	 *
	 * @param uri the URI to PUT
	 * @param o   the instance to update
	 * @return the instance passed in for convenience
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public <T> T update(String uri, T o, Class<T> clazz) throws IOException {
		HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(o), getHttpPostHeaders());
		ResponseEntity<String> response = restTemplate.exchange(getApiUrl(uri), HttpMethod.PUT, request, String.class);
		return objectMapper.reader().with(injectableValues).withValueToUpdate(o).readValue(response.getBody());
	}

	/**
	 * Sends an HTTP POST request to issue a partial update. The response is loaded back into the given object.
	 *
	 * @param uri   the URI to PATCH
	 * @param clazz the response type
	 * @param u     the patch
	 * @return the updated instance for convenience
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public <T, U> T patch(String uri, Class<T> clazz, U u) throws IOException {
		//TODO implement as required
		return null;
	}

	/**
	 * Sends an HTTP POST request to create a new instance. The response is loaded back into the last
	 * parameter of the method.
	 *
	 * @param uri   the URI to POST
	 * @param o     the request object
	 * @param clazz the response type
	 * @return the populated response object
	 * @throws IOException if an error occurs
	 */
	@Override
	public <T> T create(String uri, T o, Class<T> clazz) throws IOException {
		HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(o), getHttpPostHeaders());
		ResponseEntity<String> response = restTemplate.exchange(getApiUrl(uri), HttpMethod.POST, request, String.class);
		return objectMapper.reader().with(injectableValues).withValueToUpdate(o).readValue(response.getBody());
	}

	/**
	 * Sends an HTTP POST request to create a new instance. The response is loaded back into the request
	 * object passed into the method.
	 *
	 * @param uri the URI to POST
	 * @param o   the request object
	 * @return the populated request object for convenience
	 * @throws IOException if an error occurs
	 */
	@Override
	public <T> T create(String uri, T o) throws IOException {
		//TODO implement as required
		return null;
	}

	/**
	 * Sends an HTTP DELETE request to delete an instance.
	 *
	 * @param uri the URL to DELETE
	 * @throws IOException if an error occurs
	 */
	@Override
	public void delete(String uri) throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, getHttpHeaders());
		restTemplate.delete(getApiUrl(uri), request);
	}

	/**
	 * Sends an HTTP POST request to execute an action that doesn't return a response body.
	 *
	 * @param uri the URL to POST
	 * @throws IOException if an error occurs
	 */
	@Override
	public void execute(String uri) throws IOException {
		//TODO implement as required
	}
}