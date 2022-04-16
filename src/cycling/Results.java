package cycling;

import java.time.LocalTime;
import java.time.Duration;
import java.util.Arrays;
/**
 * Results class. Produces a riders results in a stage using their time.
 *
 * @author Joshua Adebayo and Sebastian Dixon
 */
public class Results {
    //3 private instances
    private Rider rider;
    private Stage stage;
    private LocalTime[] times;

    /**
     * This is a constructor to make a Results class.
     * @param r A Rider
     * @param s The stage that the results are for
     * @param times A list of how fast the rider completed the segments in a stage
     */
    public Results(Rider r, Stage s, LocalTime... times) {
        this.rider = r;
        this.stage = s;
        this.times = new LocalTime[times.length];
        System.arraycopy(times, 0, this.times, 0, this.times.length);
    }

    /**
     * Method to get the Rider from the Results class
     * @return A rider
     */
    public Rider getRider() {return rider;}

    /**
     * Method to retrieve the stage which the results are from
     * @return A stage
     */
    public Stage getStage() {return stage;}

    /**
     * A method to get a list of times within a stage
     * @return a list of times
     */
    public LocalTime[] getTimes() {return times;}

    /**
     * Method to get how much time a rider took to complete a segment
     * @return the time it took to complete a stage
     */
    public LocalTime getElapsedTime() {
        Duration elapsed = Duration.between(times[0], times[times.length-1]);
        return LocalTime.ofNanoOfDay(elapsed.toNanos());
    }

    /**
     * Method to get the time it took for a stage to be concluded if the value gets changed
     * after the initial input
     * @return the adjusted time that it took to complete a stage
     */
    public LocalTime getAdjustedElapsedTime() {
        Results[] elapsedTimes = new Results[stage.getResults().size()];
        for (int i = 0; i < stage.getResults().size(); i++) {
            elapsedTimes[i] = stage.getResults().get(i);
        }

        Arrays.sort(elapsedTimes, new TimeComparator());

        LocalTime base = elapsedTimes[0].getElapsedTime();
        for (int i = 0; i < elapsedTimes.length; i++) {

            if (elapsedTimes[i] == this) {
                return base;
            }

            Duration diff = Duration.between(elapsedTimes[i].getElapsedTime(), elapsedTimes[i+1].getElapsedTime());
            if (diff.toMillis() > 1e+3) {
                base = elapsedTimes[i+1].getElapsedTime();
            }
        }

        return null;
    }

    /**
     * This is a method that gets the time to a segment
     * @param index The index of a stage
     * @return The time to a segment
     */
    LocalTime getTimeToSegment(int index) {
        Duration elapsedTime = Duration.between(times[0], times[index]);
        return LocalTime.ofNanoOfDay(elapsedTime.toNanos());
    }
}
