package at.htl.locker;

import java.io.*;
import java.util.Collections;
import java.util.Scanner;

public class LockerMgmt {

    final String FILENAME = "locker.csv";

    private Locker[] lockers = new Locker[101]; // lockers[0] is always null

    public LockerMgmt() {
    }

    public void rentLocker(int lockerId, String firstName, String lastName) {

        if (lockerId < 1 || lockerId > 100) {
            throw new LockerNotAvailableException(String.format("Locker %d: id is out of range", lockerId));
        }

        if (firstName == null || firstName.isEmpty()) {
            throw new LockerNotAvailableException("firstName is null or empty");
        }

        if (lastName == null || lastName.isEmpty()) {
            throw new LockerNotAvailableException("lastName is null or empty");
        }

        if (lockers[lockerId] == null) {
            lockers[lockerId] = new Locker(lockerId, firstName, lastName);
        } else {
            throw new LockerNotAvailableException(
                    "Locker " + lockerId + ": " + firstName + " " + lastName + " already occupied"
            );
        }
    }


    public Locker[] getLockers() {
        return lockers;
    }

    /**
     * Einlesen einer csv-datei aus dem Projekt-root.
     * Die eingelsenen Locker-Daten werden zum lockers-Array hinzugefügt.
     * Falls ein Spind im lockers-Array bereits belegt ist, wird der Import abgebrochen.
     *
     * @param filename
     */
    void readCsv(String filename) {

        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String[] elements = scanner.nextLine().split(";");
                int lockerId = Integer.parseInt(elements[0]);
                String firstName = elements[1];
                String lastName = elements[2];
                rentLocker(lockerId, firstName, lastName);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Schreiben des lockers-Array in eine csv-datei im Projekt-root.
     *
     * @param filename
     */
    void writeCsv(String filename) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Locker locker : lockers) {
                if (locker != null) {
                    writer.printf("%s;%s;%s\n", locker.getLockerId(), locker.getFirstName(), locker.getLastName());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printOnScreen() {
        System.out.println("Folgende Spinde sind reserviert: ");
        for (Locker locker : lockers) {
            if (locker != null) {
                System.out.println(locker);
            }
        }
    }


    public static void main(String[] args) {

        String abfrage = "Möchten Sie einen weiteren Spind reservieren? (y/n): ";

        LockerMgmt lm = new LockerMgmt();

        lm.readCsv(lm.FILENAME);
        System.out.printf("Spinde aus Datei '%s' eingelesen\n\n", lm.FILENAME);

        lm.printOnScreen();
        System.out.println();


        Scanner scanner = new Scanner(System.in);

        System.out.printf(abfrage);
        String antwort = scanner.nextLine();
        System.out.println();

        while (antwort.equals("y")) {

            try {
                System.out.print("Spind-Nummer : ");
                int lockerId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Vorname      : ");
                String firstName = scanner.nextLine();
                System.out.print("Nachname     : ");
                String lastName = scanner.nextLine();

                lm.rentLocker(lockerId, firstName, lastName);

            } catch (LockerNotAvailableException e) {
                System.out.printf("FEHLER: %s\n\n", e.getMessage());
            }
            System.out.printf(abfrage);
            antwort = scanner.nextLine();
            System.out.println();
        }


        lm.printOnScreen();
        System.out.println();

        lm.writeCsv(lm.FILENAME);
        System.out.printf("Daten in Datei '%s' gespeichert", lm.FILENAME);
    }

}
