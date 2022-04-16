package cycling;

import java.util.Comparator;

/**
 * SegmentTImeComparator class. This class is used to compare rider times with each other
 *
 * @author Sebastian Dixon and Joshua Adebayo
 */
public class SegmentTimeComparator implements Comparator<Results> {
    private int count;

    /**
     * This is a method to assign the count, which is the segment index
     * @param count The segment index
     */
    public SegmentTimeComparator(int count) {
        this.count = count;
    }

    public int compare(Results r1, Results r2) {
        return r1.getTimeToSegment(count).compareTo(r2.getTimeToSegment(count));
    }
}
