package cycling;

/**
 * SprintSegment class. Extends from Segment class to assign points to riders in a sprint segment.
 *
 * @author Joshua Adebayo and Sebastian Dixon
 */
public class SprintSegment extends Segment {

    /**
     * Constructor for SprintSegment class.
     * @param stage Which stage within a race the segment is in
     * @param position The position within a stage that the segment lies
     */
    public SprintSegment(Stage stage, double position) {
        super(stage, SegmentType.SPRINT, position);
    }

    /**
     * Boolean method to confirm that the segment is a sprint segment
     * @return true
     */
    public boolean isSprint() {
        return true;
    }

    /**
     * Boolean method to confirm that the segment isn't a climb segment
     * @return
     */
    public boolean isClimb() {
        return false;
    }

}
