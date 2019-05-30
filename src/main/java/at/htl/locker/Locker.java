package at.htl.locker;

public class Locker {

    private int lockerId;
    private String firstName;
    private String lastName;

    public Locker(int lockerId, String firstName, String lastName) {
        this.setLockerId(lockerId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }


    public int getLockerId() {
        return lockerId;
    }

    public void setLockerId(int lockerId) {
        this.lockerId = lockerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Spind %2d: %s %s", lockerId, firstName, lastName);
    }
}
