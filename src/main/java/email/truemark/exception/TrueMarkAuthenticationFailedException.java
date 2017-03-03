package email.truemark.exception;

import lombok.NoArgsConstructor;

/**
 * @author Erik R. Jensen
 */
@NoArgsConstructor
public class TrueMarkAuthenticationFailedException extends TrueMarkException {

  public TrueMarkAuthenticationFailedException(String message) {
    super(message);
  }

  public TrueMarkAuthenticationFailedException(String message, Throwable t) {
    super(message, t);
  }

  public TrueMarkAuthenticationFailedException(Throwable t) {
    super(t);
  }
}
