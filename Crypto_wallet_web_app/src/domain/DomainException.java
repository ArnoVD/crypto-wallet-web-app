package domain;

public class DomainException extends RuntimeException {
    /* DomainException = IllegalArgumentException doordat er extends wordt van RunTimeException. */
    public DomainException(String message) {
        super(message);
    }
}
