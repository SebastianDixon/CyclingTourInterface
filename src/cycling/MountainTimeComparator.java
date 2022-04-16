package cycling;

import java.util.Comparator;
/**
 * MountainTimeComparator class. This class is used to compare how long it took riders to complete
 * the same mountain stage.
 *
 * @author Sebastian Dixon and Joshua Adebayo
 */
public class MountainTimeComparator implements Comparator<Results> {
    private final int count;
    /**
     * This is a method to get the count, this is the placement of the mountain segment within a
     * stage.
     * @param count the placement of the mountain segment within a stage.
     */
    public MountainTimeComparator(int count) {
        this.count = count;
    }

    public int compare(Results r1, Results r2) {
        return r1.getTimes()[count].compareTo(r2.getTimes()[count]);
    }

}
