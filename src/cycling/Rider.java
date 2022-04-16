package cycling;

/**
 * Rider class. A rider must be in a team to participate in any events.
 *
 * @author Joshua Adebayo and Sebastian Dixon
 *
 */
public class Rider {
    //5 private instances
    private static int s_riderId; //This is static so the value is different in each class
    private int m_riderId;
    private int yearOfBirth;
    private Team riderTeam;
    private String riderName;

    /**
     * The constructor for the Rider class. This is called in the MiniCyclingPortalInterface with
     * the createRider method
     * @param riderName The name of the Rider
     * @param riderTeam The team of the Rider
     * @param YearOfBirth Year of birth for the Rider
     */
    public Rider(String riderName, Team riderTeam, int YearOfBirth) {
        this.m_riderId = ++s_riderId; //Doing this makes each Rider's ID unique
        this.riderName = riderName;
        this.riderTeam = riderTeam;
        this.yearOfBirth = YearOfBirth;
    }

    /**
     * Method to get a Rider's unique ID
     * @return Rider ID
     */
    public int getRiderId(){return m_riderId;}

    /**
     * Method to get a Rider's team
     * @return The Rider's team
     */
    public Team getRiderTeam(){return riderTeam;}

    /**
     * Method to a Rider's Year of birth
     * @return A Rider's Year of birth
     */
    public int getYearOfBirth(){return yearOfBirth;}

    /**
     * Method to get a Rider's name
     * @return A Riders name
     */
    public String getRiderName(){return riderName;}

    /**
     * This is a method used to get a riders points in a given stage
     * @param stage The stage that points are being requested from
     * @param rank The  rider's rank in a stage
     * @return A rider's point in a stage
     */
    public int getPointsInStage(Stage stage, int rank) {
        int points = 0;

        points += stage.pointsForRank(rank);
        points += stage.getSprintPoints(this);

        return points;
    }

    /**
     * This is a method used to get a riders points in a mountain stage
     * @param stage The stage that points are being requested from
     * @return A rider's point in a stage
     */
    public int getMountainPointsInStage(Stage stage) {
        return stage.getClimbPoints(this);
    }

    /**
     * toString to output all the relevant information when a Rider is called
     * @return Rider ID, Rider team, Rider name, Year of birth
     */
    public String toString() {return "Id:"+m_riderId+",Team:"+riderTeam+",Name:"+riderName+",Born:"+yearOfBirth;}

    /**
     * This method creates a default value for a rider ID
     */
    public static void setDefaultId() {
        s_riderId = 0;
    }
}
