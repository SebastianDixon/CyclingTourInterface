package cycling;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Map;

public class AdjustTimeComparator implements Comparator<Map.Entry<Rider,LocalTime>> {
    /**
     * AdjustTimeComparator class. This class is used to change the time of completion for timed
     * durations for rider's
     *
     * @author Sebastian Dixon and Joshua Adebayo
     */
    public int compare(Map.Entry<Rider, LocalTime> r1, Map.Entry<Rider,LocalTime> r2) {
        return r1.getValue().compareTo(r2.getValue());
    }

}
