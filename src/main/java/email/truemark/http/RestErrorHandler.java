package email.truemark.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import email.truemark.error.TrueMarkError;
import email.truemark.exception.TrueMarkBadRequestException;
import email.truemark.exception.TrueMarkNotFoundException;
import email.truemark.exception.TrueMarkConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Dilip S Sisodia
 */
@Slf4j
public class RestErrorHandler implements ResponseErrorHandler {
	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		HttpStatus status = clientHttpResponse.getStatusCode();
		HttpStatus.Series series = status.series();
		return HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series);
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();

		if(clientHttpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
			List<TrueMarkError> errors;
			JsonNode node = new ObjectMapper().readValue(clientHttpResponse.getBody(), JsonNode.class);
			log.debug(HttpStatus.BAD_REQUEST + ": Bad request exception");
			if(node.has("field")) {
				TrueMarkError error = objectMapper.readValue(node.toString(), TrueMarkError.class);
				errors = Collections.singletonList(error);
			} else {
				errors = objectMapper.readValue(node.toString(), new TypeReference<List<TrueMarkError>>(){});
			}
			throw new TrueMarkBadRequestException("Bad request: " + errors.get(0).getField() + " " +
					errors.get(0).getMessage(), errors);
		}
		if(clientHttpResponse.getStatusCode() == HttpStatus.CONFLICT) {
			TrueMarkError[] errors = objectMapper.readValue(clientHttpResponse.getBody(), TrueMarkError[].class);
			log.debug(HttpStatus.CONFLICT + ": Conflict exception");
			throw new TrueMarkConflictException(errors[0].getMessage());
		}
		if(clientHttpResponse.getStatusCode() == HttpStatus.FORBIDDEN) {
			//TODO implement me
		}
		if(clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
			TrueMarkError[] errors = objectMapper.readValue(clientHttpResponse.getBody(), TrueMarkError[].class);
			log.debug(HttpStatus.NOT_FOUND + ": Not found exception" + errors.toString());
			throw new TrueMarkNotFoundException(errors[0].getMessage());
		}
	}
}