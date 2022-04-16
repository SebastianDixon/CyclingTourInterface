package cycling;

/**
 * Segment class. A Segment is a section within a stage and ClimbSegment and SprintSegment extend from
 * this class to assign points to riders
 *
 * @author Sebastian Dixon ad Joshua Adebayo
 */
public class Segment {
    //5 protected instances
    protected static int s_segmentId; //This is static so the value remains the same in each class
    protected int m_segmentId;
    protected Stage stage;
    protected SegmentType type;
    protected double positionInStage;

    /**
     * Constructor for a Segment
     * @param stage The stage where the segment is
     * @param type What type of segment it is
     * @param positionInStage The location of the segment within a stage
     */
    public Segment(Stage stage, SegmentType type, double positionInStage) {
        this.m_segmentId = ++ s_segmentId;//Doing this makes each Segment ID unique
        this.stage = stage;
        this.type = type;
        this.positionInStage = positionInStage;
    }

    /**
     * Method to get a segment's ID
     * @return A unique ID number for the segment
     */
    public int getSegmentId() {
        return m_segmentId;
    }

    /**
     * Method to get the stage that the segment is in
     * @return A stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * A method to get what type of segment a segment is
     * @return A type of segment
     */
    public SegmentType getType() {
        return type;
    }

    /**
     * Method to get the position of a segment within a stage
     * @return The position of the Segment
     */
    public double getPositionInStage() {
        return positionInStage;
    }

    /**
     * Boolean method to check if a segment is a sprint of a climb segment after using enumeration
     * to assign the segment type with SegmentType.java
     * @return true or false
     */
    public boolean climbSegment() {
        return switch (type) {
            case SPRINT -> false;
            case C1, C2, C3, C4, HC -> true;
        };
    }

    /**
     * Boolean method to check if a segment is a sprint or climb segment by using the
     * climbSegment() method
     * @return true or false
     */
    public boolean sprintSegment() {
        return !climbSegment();
    }

    /**
     * toString to return all relevant information about a Segment when called
     * @return Segment ID, what stage a segment is in, a segments position in the stage, what type
     * of segment it is
     */
    public String toString() {
        return "Id:"+m_segmentId+"," +
                "stage:"+stage+",position:"+positionInStage+",type:"+type;
    }
    /**
     * Boolean method to check if a segment is a race segment or not
     */
    boolean isSprint() {  return type == SegmentType.SPRINT;  }

    /**
     * Boolean method to check if a segment is climb segment or not by using the
     * isSprint() method
     */
    boolean isClimb() {  return !isSprint();  }

    /**
     * This method sets a default value for a segment's ID
     */
    public static void setDefaultId() {
        s_segmentId = 0;
    }

}
