package at.htl.locker.demo;

public class StaticLocker {
    static int lockerId;
    static String firstName;
    static String lastName;

    public StaticLocker() {
    }

    @Override
    public String toString() {
        return lockerId + ";" + firstName + ";" + lastName;
    }
}
