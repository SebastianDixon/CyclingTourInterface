package cycling;
/**
 * ClimbSegment class. This class assigns points to riders during for the climb segments in a race depending on the
 * average gradient
 *
 * @author Sebastian Dixon and Joshua Adebayo
 */
public class ClimbSegment extends Segment{
    // 2 private instances
    private double averageGradient;
    private double length;

    /**
     * Constructor for a ClimbSegment
     * @param stage Which stage within a race the segment is in
     * @param position The position within a stage that the segment lies
     * @param type The type of segment
     * @param averageGradient The average gradient of the segment
     * @param length The length of the segment
     */
    public ClimbSegment(Stage stage, double position, SegmentType type, double averageGradient, double length) {
        super(stage, type, position);
        this.averageGradient = averageGradient;
        this.length = length;
    }

    /**
     * Boolean method to confirm that the segment is a climb segment
     * @return true
     */
    boolean isClimb() {
        return true;
    }

    /**
     * Boolean method to confirm that the segment isn't a sprint segment
     * @return false
     */
    boolean isSprint() {
        return false;
    }

    /**
     * Method to assign points to a rider during a climb segment based on their rank
     * @param rank what position a rider finished the segment in
     * @return How many points that have been given to a rider in a climb segment
     */
    public int mountainPoints(int rank) {
        return switch (type) {
            case C1 -> pointsFor1C(rank);
            case C2 -> pointsFor2C(rank);
            case C3 -> pointsFor3C(rank);
            case C4 -> pointsFor4C(rank);
            case HC -> pointsForHC(rank);
            default -> 0;
        };
    }

    /**
     * A method to assign points to a rider during a HC segment
     * @param rank what position a rider finished the segment in
     * @return How many points that have been given to a rider a climb segment
     */
    static public int pointsForHC(int rank) {
        return switch (rank) {
            case 1 -> 20;
            case 2 -> 15;
            case 3 -> 12;
            case 4 -> 10;
            case 5 -> 8;
            case 6 -> 6;
            case 7 -> 4;
            case 8 -> 2;
            default -> 0;
        };
    }
    /**
     * A method to assign points to a rider during a 1C climb segment
     * @param rank what position a rider finished the segment in
     * @return How many points that have been given to a rider a climb segment
     */
    static public int pointsFor1C(int rank) {
        return switch (rank) {
            case 1 -> 10;
            case 2 -> 8;
            case 3 -> 6;
            case 4 -> 4;
            case 5 -> 2;
            case 6 -> 1;
            default -> 0;
        };
    }
    /**
     * A method to assign points to a rider during a 2C segment
     * @param rank what position a rider finished the segment in
     * @return How many points that have been given to a rider a climb segment
     */
    static public int pointsFor2C(int rank) {
        return switch (rank) {
            case 1 -> 5;
            case 2 -> 3;
            case 3 -> 2;
            case 4 -> 1;
            default -> 0;
        };
    }
    /**
     * A method to assign points to a rider during a 3C segment
     * @param rank what position a rider finished the segment in
     * @return How many points that have been given to a rider a climb segment
     */
    static public int pointsFor3C(int rank) {
        return switch (rank) {
            case 1 -> 2;
            case 2 -> 1;
            default -> 0;
        };
    }
    /**
     * A method to assign points to a rider during a 4C segment
     * @param rank what position a rider finished the segment in
     * @return How many points that have been given to a rider a climb segment
     */
    static public int pointsFor4C(int rank) {
        if (rank == 1) {
            return 1;
        }
        return 0;
    }

    /**
     * A method to get the average gradient in a segment
     * @return The average gradient in ta segment
     */
    public double getAverageGradient() {
        return averageGradient;
    }

    /**
     * A method to get the length of a segment
     * @return The length of a segment
     */
    public double getLength() {
        return length;
    }
}
