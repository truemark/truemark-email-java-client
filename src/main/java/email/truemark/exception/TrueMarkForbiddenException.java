package email.truemark.exception;

import lombok.NoArgsConstructor;

/**
 * @author Erik R. Jensen
 */
@NoArgsConstructor
public class TrueMarkForbiddenException extends TrueMarkException {

  public TrueMarkForbiddenException(String message) {
    super(message);
  }

  public TrueMarkForbiddenException(String message, Throwable t) {
    super(message, t);
  }

  public TrueMarkForbiddenException(Throwable t) {
    super(t);
  }

}
