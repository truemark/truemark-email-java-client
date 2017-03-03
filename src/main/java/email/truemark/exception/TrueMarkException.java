package email.truemark.exception;

import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Base exception class for TrueMark Client.
 *
 * @author Dilip S Sisodia
 */
@NoArgsConstructor
public class TrueMarkException extends IOException {

  private static final long serialVersionUID = -8458422627763003734L;

  public TrueMarkException(String message) {
    super(message);
  }

  public TrueMarkException(String message, Throwable t) {
    super(message, t);
  }

  public TrueMarkException(Throwable t) {
    super(t);
  }
}
