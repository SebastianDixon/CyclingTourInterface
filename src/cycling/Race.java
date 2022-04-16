package cycling;
import java.util.ArrayList;

/**
 * Race class. A race is a collection of stages that have segments within them.
 *
 * @author Sebasatian Dixon and Joshua Adebayo
 */
public class Race {
    // 6 private instances
    private static int s_raceId; //This is static so the value remains the same in each class
    private int m_raceId;
    private String name;
    private final String description;
    //The following have been made to be Arraylists so elements can be taken out and added at anytime
    private ArrayList<Stage> stages = new ArrayList<>();
    private ArrayList<Results> results = new ArrayList<>();

    /**
     * This method constructs a Race this is called in createRace in the  MiniCyclingPortalInterface
     * @param name Race's Name
     * @param description A description of the race
     */
    public Race(String name, String description) {
        this.m_raceId = ++s_raceId;//Doing this makes each race's ID unique
        this.name = name;
        this.description = description;
    }

    /**
     * This method gets the RaceID of a race
     * @return A unique raceID for a race
     */
    public int getRaceId() {return m_raceId;}

    /**
     * This is a method that gets the name of a race
     * @return the name of a race
     */
    public String getName() {return name;}

    /**
     * This is a method that returns the description of a Race
     * @return
     */
    public String getDescription() {return description;}

    /**
     * This is a method used to get the stages within a race
     * @return An Arraylist of the stages in a race
     */
    public ArrayList<Stage> getStages() {
        return stages;
    }

    /**
     * This method gets the results of a race
     * @return An Arraylist of the results from a race
     */
    public ArrayList<Results> getResults() {
        return results;
    }

    /**
     * This method adds a stage to the 'stages' Arraylist
     * @param stage A stage
     */
    public void addStage(Stage stage) {
        stages.add(stage);
    }

    /**
     * This method removes a stage from the 'stages' Arraylist
     * @param s A stage
     * @throws IDNotRecognisedException throws 'invalid stage' if the stage already exists
     */
    public void removeStage(Stage s) throws IDNotRecognisedException {
        if (!stages.contains(s)) {
            throw new IDNotRecognisedException("invalid stage");
        }
        stages.remove(s);
    }

    /**
     * This method is used to check if the 'stages' Arraylist contains a stage
     * @param s A stage
     * @return Either true or false depending on if the given stage is within the 'stages'
     * Arraylist
     */
    public boolean containsStage(Stage s) {
        return stages.contains(s);
    }

    /**
     * This method adds a race to the Arraylist 'results'
     * @param r The results of a race
     */
    public void addResults(Results r) {
        results.add(r);
    }

    /**
     * This method removes a race from the 'results' Arraylist
     * @param r A race
     * @throws IDNotRecognisedException Throw an exception if the given result doesn't exist within
     * the 'results Arraylist
     */
    public void removeResults(Results r) throws IDNotRecognisedException {
        if (!results.contains(r)) {
            throw new IDNotRecognisedException("invalid result");
        }
        results.remove(r);
    }

    /**
     * toString that outputs the relevant information about a race when the race is called
     * @return the raceID, name , description of a  specific race
     */
    public String toString() {return "Id="+m_raceId+
            "name="+name+",description="+description;}

    /**
     * This method set's a default value for a riders ID
     */
    public static void setDefaultId() {
        s_raceId = 0;
    }
}
