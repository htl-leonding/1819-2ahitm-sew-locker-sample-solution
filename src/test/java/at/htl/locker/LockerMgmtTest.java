package at.htl.locker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class LockerMgmtTest {

    @Test
    public void test01_rentLocker_OK() {

        LockerMgmt lm = new LockerMgmt();
        Locker[] lockers = lm.getLockers();

        lm.rentLocker(3, "Mimi", "Musterfrau");
        lm.rentLocker(5, "Maxi", "Mustermann");
        lm.rentLocker(15, "Susi", "Super");

        Locker actual = lockers[3];

        assertThat(actual.getFirstName(), is("Mimi"));
        assertThat(actual.getLastName(), is("Musterfrau"));
        assertThat(actual.getLockerId(), is(3));

        actual = lockers[5];

        assertThat(actual.getFirstName(), is("Maxi"));
        assertThat(actual.getLastName(), is("Mustermann"));
        assertThat(actual.getLockerId(), is(5));

        actual = lockers[15];

        assertThat(actual.getFirstName(), is("Susi"));
        assertThat(actual.getLastName(), is("Super"));
        assertThat(actual.getLockerId(), is(15));

    }

    @Test
    public void test02_rentLocker_doppelte_Werte() {

        LockerMgmt lm = new LockerMgmt();
        Locker[] lockers = lm.getLockers();

        lm.rentLocker(3, "Mimi", "Musterfrau");
        lm.rentLocker(5, "Maxi", "Mustermann");

        try {
            lm.rentLocker(3, "Susi", "Super");
        } catch (LockerNotAvailableException e) {
            assertThat(e.getClass().getSimpleName(), is("LockerNotAvailableException"));
            assertThat(e.getMessage(), is("Locker 3: Susi Super already occupied"));
        }

        // besser: aber noch nicht gelernt ---------------------------------
        // https://stackoverflow.com/a/44787039
        LockerNotAvailableException ex = Assertions.assertThrows(LockerNotAvailableException.class, () -> {
            lm.rentLocker(3, "Susi", "Super");
        });
        assertThat(ex.getMessage(), is("Locker 3: Susi Super already occupied"));
        // -----------------------------------------------------------------

        Locker actual = lockers[3];

        assertThat(actual.getFirstName(), is("Mimi"));
        assertThat(actual.getLastName(), is("Musterfrau"));
        assertThat(actual.getLockerId(), is(3));

        actual = lockers[5];

        assertThat(actual.getFirstName(), is("Maxi"));
        assertThat(actual.getLastName(), is("Mustermann"));
        assertThat(actual.getLockerId(), is(5));
    }

    @Test
    public void test03_readCsv() {
        LockerMgmt lm = new LockerMgmt();
        Locker[] lockers = lm.getLockers();
        lm.readCsv(lm.FILENAME);

        // Überprüfen, ob vor und nach den gemieteten Spinden, keine anderen Spinde eingetragen wurden
        assertThat(lockers[0], is(nullValue()));
        assertThat(lockers[1], is(nullValue()));
        assertThat(lockers[4], is(nullValue()));
        assertThat(lockers[6], is(nullValue()));
        assertThat(lockers[20], is(nullValue()));


        assertThat(lockers[3].getFirstName(), is("Max"));
        assertThat(lockers[3].getLastName(), is("Mustermann"));
        assertThat(lockers[3].getLockerId(), is(3));
    }

    /**
     * Hier muss man manuell die neu erstellte csv-Datei prüfen
     */
    @Test
    public void test04_writeCsv_noAssert() {
        LockerMgmt lm = new LockerMgmt();
        lm.readCsv(lm.FILENAME);
        lm.writeCsv("locker_test04.csv");
    }

    /**
     * Haben wir noch nicht gelernt.
     */
    @Test
    public void test05_writeCsv() {
        final String FILENAME_FOR_TEST = "locker_test05.csv";
        LockerMgmt lm = new LockerMgmt();
        lm.readCsv(lm.FILENAME);
        lm.writeCsv(FILENAME_FOR_TEST);

        try (Stream<String> stream = Files.lines(Paths.get(FILENAME_FOR_TEST))) {

            Spliterator<String> spliterator = stream.spliterator();
            spliterator.tryAdvance(line -> assertThat(line, is("3;Max;Mustermann")));
            spliterator.tryAdvance(line -> assertThat(line, is("5;Mimi;Musterfrau")));
            spliterator.tryAdvance(line -> assertThat(line, is("15;Susi;Super")));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // löschen des erstellten File
        try {
            Files.delete(Paths.get(FILENAME_FOR_TEST));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void test06_parameter_out_of_range_FAIL() {

        LockerMgmt lm = new LockerMgmt();

        try {
            lm.rentLocker(200, "Toni", "Toll");
        } catch (LockerNotAvailableException e) {
            assertThat(e.getClass().getSimpleName(), is("LockerNotAvailableException"));
            assertThat(e.getMessage(), is("Locker 200: id is out of range"));
        }

    }

    @Test
    public void test07_firstName_is_null_or_emptyFAIL() {

        LockerMgmt lm = new LockerMgmt();

        try {
            lm.rentLocker(2, null, "Nachname");
        } catch (LockerNotAvailableException e) {
            assertThat(e.getClass().getSimpleName(), is("LockerNotAvailableException"));
            assertThat(e.getMessage(), is("firstName is null or empty"));
        }
    }

    @Test
    public void test08_lastName_is_null_or_emptyFAIL() {

        LockerMgmt lm = new LockerMgmt();

        try {
            lm.rentLocker(2, "Vorname", null);
        } catch (LockerNotAvailableException e) {
            assertThat(e.getClass().getSimpleName(), is("LockerNotAvailableException"));
            assertThat(e.getMessage(), is("lastName is null or empty"));
        }
    }

}