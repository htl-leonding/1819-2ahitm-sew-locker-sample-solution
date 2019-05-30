package at.htl.locker.demo;

import java.util.Scanner;

public class NextIntProblemDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Geben Sie eine Ganzzahl ein      : ");
        int zahl = sc.nextInt();
        sc.nextLine();

        System.out.print("Geben Sie einen Text ein         : ");
        String text1 = sc.nextLine();
        System.out.println();

        System.out.print("Geben Sie einen zweiten Text ein : ");
        String text2 = sc.nextLine();

    }



}
