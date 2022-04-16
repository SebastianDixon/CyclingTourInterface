package cycling;
import java.util.Comparator;


/**
 * TimeComparator class. This class compares how long it took riders to complete a section.
 *
 * @author Joshua Adebauo and Sebastian Dixon
 */
public class TimeComparator implements Comparator<Results> {

    public int compare(Results r1, Results r2) {
        return r1.getElapsedTime().compareTo(r2.getElapsedTime());
    }

}
