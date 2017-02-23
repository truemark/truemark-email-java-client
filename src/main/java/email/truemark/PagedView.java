package email.truemark;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import email.truemark.http.RestClient;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Dilip S Sisodia
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(exclude = {"links"})
public class PagedView<R> implements Serializable {

	private static final long serialVersionUID = -3108129232829970870L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	@Setter(AccessLevel.NONE)
	private List<R> content;

	@Setter(AccessLevel.NONE)
	protected Map<String, String> links;

	@Setter(AccessLevel.NONE)
	private PageMetadata page;

	public boolean hasNext() {
		return links.containsKey("next");
	}

	public boolean hasPrev() {
		return links.containsKey("prev");
	}

	public PagedView<R> next() throws IOException {
		return hasNext()
				? restClient.get(links.get("next"), PagedView.class, this.getClass())
				: this;

	}

	public PagedView<R> prev() throws IOException {
		return hasPrev()
				? restClient.get(links.get("prev"), PagedView.class, this.getClass())
				: this;
	}


	@Data
	public static class PageMetadata {

		@Setter(AccessLevel.NONE)
		private int number;

		@Setter(AccessLevel.NONE)
		private int pages;

		@Setter(AccessLevel.NONE)
		private long count;
	}
}