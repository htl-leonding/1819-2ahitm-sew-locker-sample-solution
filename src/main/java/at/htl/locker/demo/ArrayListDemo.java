package at.htl.locker.demo;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            list.add(null);
        }

        if (list.get(50) == null) {
            System.out.println("50 is null");
        }

        list.set(30,"hello world");
        System.out.println(list.get(30));

    }

}
