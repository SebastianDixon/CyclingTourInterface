package cycling;
import java.util.ArrayList;

/**
 * Team class. A team is a collection of riders.
 *
 * @author Joshua Adebayo and Sebastian Dixon
 */
public class Team{
    private static int s_teamId;
    private int m_teamId;
    private String teamName;
    private String teamDescription;
    private ArrayList<Rider> teamRiders;

    /**
     * This is a constructor for a Team class. This is called in the MiniCyclingPortalInterface
     * with the createTeam method
     * @param teamName The name of the team
     * @param teamDescription A description of the team
     */
    public Team(String teamName, String teamDescription) {
        this.m_teamId = ++s_teamId;//Doing this makes each Team's ID unique
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamRiders = new ArrayList<>();
    }

    /**
     * This is a method to get each team's unique ID number
     * @return A team's ID number
     */
    public int getTeamId() {
        return m_teamId;
    }

    /**
     * This is a method to get a team's name
     * @return The team's name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * This is a method to get a team's description
     * @return The teams description
     */
    public String getTeamDescription() {
        return teamDescription;
    }

    /**
     * This is a method that returns all the riders within a team . We've made this to be an
     * Arraylist so that the number of riders in a team can be dynamic
     * @return The riders within a team
     */
    public ArrayList<Rider> getRiders() {
        return teamRiders;
    }

    /**
     * This method gets a riders order within a team by searching through all the rider by
     * ID
     * @param r A rider
     * @return 'incorrect id' if rider is not found
     * @throws IDNotRecognisedException This exception is to make sure that the rider is in the
     * team
     */
    public int riderIndex(Rider r) throws IDNotRecognisedException {
        int index = 0;
        for(Rider rider:teamRiders){
            if(rider.getRiderId() == r.getRiderId()) {
                return index;
            }
            ++index;
        }
        throw new IDNotRecognisedException("incorrect id");
    }

    /**
     * This is a method to add a rider to the teamRider Arraylist within a team
     * @param r A rider
     */
    public void addRider(Rider r) {
        teamRiders.add(r);
    }

    /**
     * This is a method to remove a rider from the teamRider Arraylist within a team
     * @param r A rider
     * @throws IDNotRecognisedException This exception ensures that the rider is the list
     */
    public void removeRider(Rider r) throws IDNotRecognisedException {
        int index = riderIndex(r);
        teamRiders.remove(index);
    }

    /**
     * A method to output if a rider is in a team
     * @param r A rider
     * @return true or false
     */
    public boolean includeRider(Rider r) {
        try {
            riderIndex(r);
        } catch (IDNotRecognisedException e) {
            return false;
        } return true;
    }

    /**
     * toString to output all the relevant information for a team
     * @return A team name and description
     */
    public String toString() {
        return "Team:"+getTeamName()+",Desc:"+getTeamDescription();
    }

    /**
     * This method set's a default value for a team's ID
     */
    public static void setDefaultId() {
        s_teamId = 0;
    }
}
