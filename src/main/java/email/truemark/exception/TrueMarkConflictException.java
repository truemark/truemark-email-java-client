package email.truemark.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Conflict exception class.
 *
 * @author Dilip S Sisodia
 */
@Data
@NoArgsConstructor
public class TrueMarkConflictException extends TrueMarkException {

  private static final long serialVersionUID = -8308816875012415288L;

  private String detailMessage;

  public TrueMarkConflictException(String message) {
    super(message);
    this.detailMessage = message;
  }

  public TrueMarkConflictException(String message, Throwable t) {
    super(message, t);
  }

  public TrueMarkConflictException(Throwable t) {
    super(t);
  }
}
