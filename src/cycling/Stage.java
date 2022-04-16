package cycling;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Stage class. A stage is part of a race and is split into segments.
 *
 * @author Joshua Adebayo and Sebastian Dixon
 */
public class Stage {
    //10 private instances
    private static int s_stageId;
    private int m_stageId;
    private String name;
    private String description;
    private double length;
    private LocalDateTime start;
    private StageType type;
    private StageState state;
    //Made to be an Arraylist so elements can be removed and added
    private ArrayList<Segment> segments = new ArrayList<>();
    private ArrayList<Results> results = new ArrayList<>();
    /**
     * Constructor for a Stage class
     * @param race What race the stage is in
     * @param name The name of the stage
     * @param desc Description of the stage
     * @param length How long the stage is
     * @param start The time that the stage started
     * @param type What type of stage it is
     */
    public Stage(Race race, String name, String desc, double length, LocalDateTime start,
                 StageType type) {
        this.m_stageId = ++s_stageId;//Doing this makes each stage ID unique
        this.name = name;
        this.description = desc;
        this.length = length;
        this.start = start;
        this.type = type;
        this.state = StageState.STAGE_PREPARING;
    }

    /**
     * Method to get a unique stage ID
     * @return Stage ID
     */
    public int getStageId() {
        return m_stageId;
    }

    /**
     * Method to get the name of a Stage
     * @return The name of a Stage
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the description of a Stage
     * @return The description of a Stage
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to get the length of a stage
     * @return The total length of a stage
     */
    public double getLength() {
        return length;
    }


    /**
     * Method to get the time that a Stage started
     * @return The time a Stage started
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Method to what type a Stage is
     * @return What type of Stage it is
     */
    public StageType getType() {
        return type;
    }

    /**
     * Method to get the state of a Stage
     * @return The state of a Stage
     */
    public StageState getState() {
        return state;
    }

    /**
     * Method to get the segments within a Stage
     * @return An Arraylist of segments in a Stage
     */
    public ArrayList<Segment> getSegments() {
        return segments;
    }

    /**
     * Method to add a segment to the Stage
     * @param s A segment
     */
    public void addSegment(Segment s) {
        this.segments.add(s);
    }

    /**
     * Method to remove a segment from a stage
     * @param s A segment
     */
    public void removeSegment(Segment s) {
        this.segments.remove(s);
    }

    /**
     * An exception that uses enumeration to check the state of a Stage
     * @throws InvalidStageStateException checks and returns if a state is waiting or preparing
     */
    public void prepareStage() throws InvalidStageStateException {
        if (this.state == StageState.STAGE_WAITING) {
            throw new InvalidStageStateException("already waiting");
        }
        this.state = StageState.STAGE_WAITING;
    }

    /**
     * This is a method that returns the results in a stage
     * @return An array list of the results in a stage
     */
    public ArrayList<Results> getResults() {
        return results;
    }

    /**
     * This is a method to add results to the results arraylist
     * @param r The result that is being added to the list
     */
    public void addResults(Results r) {
        results.add(r);
    }

    /**
     * This is a method that removes a result from the results arraylist
     * @param r The result that's being removed
     * @throws IDNotRecognisedException
     */
    public void removeResults(Results r) throws IDNotRecognisedException {
        if (!results.contains(r)) {
            throw new IDNotRecognisedException("results doesn't exists");
        }
        results.remove(r);
    }

    /**
     * This is a method that is used to assign points based on what type of classification and
     * where a person ranked.
     * @param rank The position a rider finished a stage.
     * @return How many points that are being assigned
     */
    public int pointsForRank(int rank) {
        return switch (this.type) {
            case FLAT -> flatPoints(rank);
            case HIGH_MOUNTAIN, TT -> HMTTISPoints(rank);
            case MEDIUM_MOUNTAIN -> MMPoints(rank);
        };
    }

    /**
     * This method assigns point during the flat stages in a race based on a riders rank
     * @param rank The position a rider finished a stage.
     * @return How many points that is given to a rider
     */
    static public int flatPoints(int rank) {
        return switch (rank) {
            case 1 -> 50;
            case 2 -> 30;
            case 3 -> 20;
            case 4 -> 18;
            case 5 -> 16;
            case 6 -> 14;
            case 7 -> 12;
            case 8 -> 10;
            case 9 -> 8;
            case 10 -> 7;
            case 11 -> 6;
            case 12 -> 5;
            case 13 -> 4;
            case 14 -> 3;
            case 15 -> 2;
            default -> 0;
        };
    }

    /**
     * This method assigns points for during a mountain stage to a rider based on their rank
     * @param rank The position a rider finished a stage.
     * @return How many points that is given to a rider
     */
    static public int MMPoints(int rank) {
        return switch (rank) {
            case 1 -> 30;
            case 2 -> 25;
            case 3 -> 22;
            case 4 -> 19;
            case 5 -> 17;
            case 6 -> 15;
            case 7 -> 13;
            case 8 -> 11;
            case 9 -> 9;
            case 10 -> 7;
            case 11 -> 6;
            case 12 -> 5;
            case 13 -> 4;
            case 14 -> 3;
            case 15 -> 2;
            default -> 0;
        };
    }

    /**
     * This method assigns points for time trailed stages.
     * @param rank The position a rider finished a stage.
     * @return How many points that is given to a rider
     */
    static public int HMTTISPoints(int rank) {
        return switch (rank) {
            case 1 -> 20;
            case 2 -> 17;
            case 3 -> 15;
            case 4 -> 13;
            case 5 -> 11;
            case 6 -> 10;
            case 7 -> 9;
            case 8 -> 8;
            case 9 -> 7;
            case 10 -> 6;
            case 11 -> 5;
            case 12 -> 4;
            case 13 -> 3;
            case 14 -> 2;
            case 15 -> 1;
            default -> 0;
        };
    }

    /**
     * This is a method used to get the points a rider has accumulated in the sprint stage of a
     * race
     * @param r A rider
     * @return A riders points from the sprint stage
     */
    public int getSprintPoints(Rider r) {
        int points = 0;
        for (Segment s : segments) {
            if (s.isSprint()) {

                Results[] sortedResults = new Results[getResults().size()];
                for (int i = 0; i < sortedResults.length; i++) {
                    sortedResults[i] = getResults().get(i);
                }

                Arrays.sort(sortedResults);

                for (int i = 0; i < sortedResults.length; i++) {
                    if (sortedResults[i].getRider() == r) {
                        points += HMTTISPoints(i + 1);
                    }
                }
            }
        }
        return points;
    }

    /**
     * This is a method used to get the points accumulated by a rider in a climb stage
     * @param r A rider
     * @return A riders points from the climb stage
     */
    public int getClimbPoints(Rider r) {
        int points = 0;
        for (Segment s : segments) {
            if (s.isClimb()) {
                ClimbSegment segment = (ClimbSegment) s;

                Results[] sortedResults = new Results[getResults().size()];
                for (int i = 0; i < sortedResults.length; i++) {
                    sortedResults[i] = getResults().get(i);
                }

                Arrays.sort(sortedResults);


                for (int n = 0; n < sortedResults.length; n++) {
                    if (sortedResults[n].getRider() == r) {
                        points += segment.mountainPoints(n + 1);
                    }
                }
            }
        }
        return points;
    }

    /**
     * This is a method used to set a default value for a stage ID
     */
    public static void setDefaultId() {
        s_stageId = 0;
    }
}
