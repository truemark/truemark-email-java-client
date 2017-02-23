package email.truemark.exception;

import email.truemark.error.TrueMarkError;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilip S Sisodia
 */
@Data
@NoArgsConstructor
public class TrueMarkBadRequestException extends TrueMarkException {

	private static final long serialVersionUID = 4575363667276218487L;

	protected List<TrueMarkError> errors = new ArrayList<>();

	public TrueMarkBadRequestException(String message) {
		super(message);
	}

	public TrueMarkBadRequestException(String message, List<TrueMarkError> errors) {
		super(message);
		this.errors = errors;
	}
}