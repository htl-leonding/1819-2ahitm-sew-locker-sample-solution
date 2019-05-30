package at.htl.locker.demo;

public class StaticDemo {

    public static void main(String[] args) {

        StaticLocker.firstName = "Hans";
        StaticLocker.lastName = "Herrlich";
        StaticLocker.lockerId = 1;

        StaticLocker l1 = new StaticLocker();
        StaticLocker l2 = new StaticLocker();

        System.out.println(l1);
        System.out.println(l2);
    }

}
