package at.htl.locker;

public class LockerNotAvailableException extends RuntimeException {

    public LockerNotAvailableException() {
        super();
    }

    public LockerNotAvailableException(String message) {
        super(message);
    }
}

