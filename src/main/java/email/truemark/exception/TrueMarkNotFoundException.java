package email.truemark.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dilip S Sisodia
 */
@Data
@NoArgsConstructor
public class TrueMarkNotFoundException extends TrueMarkException {
	private static final long serialVersionUID = -1018398852945241952L;

	private String detailMessage;

	public TrueMarkNotFoundException(String message) {
		super(message);
		this.detailMessage = message;
	}

	public TrueMarkNotFoundException(String message, Throwable t) {
		super(message, t);
	}

	public TrueMarkNotFoundException(Throwable t) {
		super(t);
	}
}