package cycling;
import java.util.*;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * CyclingPortal class. Class that implements both CyclingPortalInterface
 *
 * @author Sebastian Dixon and Joshua Adebayo
 */

public class CyclingPortal implements CyclingPortalInterface {

    private ArrayList<Race> races;
    private ArrayList<Team> teams;

    /**
     * Constructor for CyclingPortal which initializes teams and races arraylist
     */
    public CyclingPortal() {
        races = new ArrayList<>();
        teams = new ArrayList<>();
    }

    /**
     * Method to get all races.
     * @return All current and previous races
     */
    public ArrayList<Race> getRaces() {
        return races;
    }

    /**
     * Method to find a rider by going through all the riders in every team using their ID
     * @param r_id A rider's ID
     * @return All relevant information for a rider
     * @throws IDNotRecognisedException Exception to ensure that a rider is assigned to the given
     * ID
     */

    public Rider findRider(int r_id) throws IDNotRecognisedException {
        for (Team t: teams) {
            for (Rider r : t.getRiders()) {
                if (r.getRiderId() == r_id) {
                    return r;
                }
            }
        }

        throw new IDNotRecognisedException(r_id+" Rider id not found");
    }



    /**
     * Method to find a team
     * @param t_id A team's ID
     * @return All relevant information for a team
     * @throws IDNotRecognisedException Exception to ensure that a team is assigned to the given
     * ID
     */
    public Team findTeam(int t_id) throws IDNotRecognisedException {
        for (Team t: teams) {
            if (t.getTeamId() == t_id) {
                return t;
            }
        }
        throw new IDNotRecognisedException("Team id not found");
    }

    /**
     * Method to find a Segment by going through all the races in every stage using their ID
     * @param seg_id A segment's ID
     * @return All relevant information for a segment
     * @throws IDNotRecognisedException Exception to ensure that a segment is assigned to the given
     * ID
     */
    public Segment findSegment(int seg_id) throws IDNotRecognisedException {
        for (Race r: races) {
            for (Stage s: r.getStages()) {
                for (Segment seg: s.getSegments()) {
                    if (seg.getSegmentId() == seg_id) {
                        return seg;
                    }
                }
            }
        }
        throw new IDNotRecognisedException("Team id not found");
    }

    /**
     * Method to find a Stage by going through all the races using their ID
     * @param s_id A stage's ID
     * @return All relevant information for a stage
     * @throws IDNotRecognisedException Exception to ensure that a stage is assigned to the given
     * ID
     */
    public Stage findStage(int s_id) throws IDNotRecognisedException {
        for (Race r: races) {
            for (Stage s: r.getStages()) {
                if (s.getStageId() == s_id) {
                    return s;
                }
            }
        }
        throw new IDNotRecognisedException("Stage id not found");
    }

    /**
     * Method to find a race
     * @param r_id A race's ID
     * @return All relevant information for a race
     * @throws IDNotRecognisedException Exception to ensure that a race is assigned to the given
     * ID
     */
    public Race findRace(int r_id) throws IDNotRecognisedException {
        for (Race r: races) {
            if(r.getRaceId() == r_id) {
                return r;
            }
        }
        throw new IDNotRecognisedException("Race id not found");
    }

    @Override
    public int[] getRaceIds() {
        int[] raceIds = new int[races.size()];
        for (int i=0; i<races.size(); i++) {
            raceIds[i] = races.get(i).getRaceId();
        }
        return raceIds;
    }

    @Override
    public int createRace(String name, String desc) throws InvalidNameException, IllegalNameException {
        if (name == null || name.equals("") || name.length() > 2000000 || name.contains(" ")) {
            throw new InvalidNameException("Invalid race name input");
        }
        for (Race r: races) {
            if (name.equals(r.getName())) {
                throw new IllegalNameException("Race name already exists");
            }
        }

        Race r = new Race(name, desc);

        races.add(r);

        return r.getRaceId();
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);
        return r.toString()+",numberOfStages="+r.getStages().size();
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);
        races.remove(r);
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);
        return r.getStages().size();
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length,
                              LocalDateTime startTime, StageType type)
            throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

        Race r = findRace(raceId);

        if (stageName == null || stageName.equals("") || stageName.length() > 2000000) {
            throw new InvalidNameException("Invalid stage name input");
        }

        for (Race race: races) {
            for (Stage stage: race.getStages()) {
                String name = stage.getName();
                if (name.equals(stageName)) {
                    throw new IllegalNameException("Stage name already exists");
                }
            }
        }

        if (length < 5) {
            throw new InvalidLengthException("Length must be more than or equal to 5km");
        }

        Stage s = new Stage(r, stageName, description, length, startTime, type);
        r.addStage(s);
        return s.getStageId();
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);
        int[] raceStages = new int[r.getStages().size()];
        for (int i=0; i<r.getStages().size(); i++) {
            raceStages[i] = r.getStages().get(i).getStageId();
        }
        return raceStages;
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);
        return s.getLength();
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);
        for (Race r: races) {
            for (Stage stage: r.getStages()) {
                if (stage == s) {
                    r.removeStage(s);
                }
            }
        }
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
                                          Double length) throws IDNotRecognisedException, InvalidLocationException,
            InvalidStageStateException, InvalidStageTypeException {

        Stage s = findStage(stageId);

        if (type == SegmentType.SPRINT) {
            throw new IllegalArgumentException("This is a climb segment, not sprint.");
        }

        if (s.getLength() < location) {
            throw new InvalidLocationException("location in stage must be in range");
        }

        if (s.getState() == StageState.STAGE_WAITING) {
            throw new InvalidStageStateException("you cannot add while waiting for results");
        }

        if (s.getType() == StageType.TT) {
            throw new InvalidStageTypeException("time trials don't contain segments");
        }

        ClimbSegment seg = new ClimbSegment(s, location, type, averageGradient, length);
        s.addSegment(seg);
        return seg.getSegmentId();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
            InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Stage s = findStage(stageId);

        if (s.getLength() < location) {
            throw new InvalidLocationException("location in stage must be in range");
        }

        if (s.getState() == StageState.STAGE_WAITING) {
            throw new InvalidStageStateException("Stage cant be removed while getting results");
        }

        if (s.getType() == StageType.TT) {
            throw new InvalidStageTypeException("time trials don't contain segments");
        }

        SprintSegment seg = new SprintSegment(s, location);
        s.addSegment(seg);
        return seg.getSegmentId();
    }

    @Override
    public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
        Segment seg = findSegment(segmentId);
        Stage s = seg.getStage();

        if (s.getState() == StageState.STAGE_WAITING) {
            throw new InvalidStageStateException("Stage cant be removed while getting results");
        }
        s.removeSegment(seg);
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        Stage s = findStage(stageId);
        s.prepareStage();
    }

    @Override
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);
        int[] segmentIds = new int[s.getSegments().size()];
        for (int i=0; i<s.getSegments().size(); i++) {
            segmentIds[i] = s.getSegments().get(i).getSegmentId();
        }
        return segmentIds;
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        for (Team t : teams) {
            if (name.equals(t.getTeamName())) {
                throw new IllegalNameException("team name used");
            }
        }

        if (name == null || name.equals("") || name.length() > 2000000 || name.contains(" ")) {
            throw new InvalidNameException("Invalid team name input");
        }

        Team t = new Team(name, description);
        teams.add(t);
        return t.getTeamId();
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        Team t = findTeam(teamId);
        teams.remove(t);
    }

    @Override
    public int[] getTeams() {
        int[] teamIds = new int[teams.size()];
        for (int i=0; i<teams.size(); i++) {
            teamIds[i] = teams.get(i).getTeamId();
        }
        return teamIds;
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        Team t = findTeam(teamId);
        int[] teamRiders = new int[teams.size()];
        for (int i=0; i<teams.size(); i++) {
            teamRiders[i] = t.getRiders().get(i).getRiderId();
        }
        return teamRiders;
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth)
            throws IDNotRecognisedException, IllegalArgumentException {

        if (name == null||yearOfBirth<1990) {
            throw new IllegalArgumentException("must have been born after 1990, cannot have empty name");
        }
        Team t = findTeam(teamID);
        Rider r = new Rider(name, t, yearOfBirth);

        t.addRider(r);

        return r.getRiderId();
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        Rider rider = findRider(riderId);
        for (Team t: teams) {
            for (Rider r: t.getRiders()) {
                if (r.getRiderId() == riderId) {
                    t.removeRider(rider);
                }
            }
        }
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
            throws IDNotRecognisedException, DuplicatedResultException,
            InvalidCheckpointsException, InvalidStageStateException {
        Stage s = findStage(stageId);
        Rider r = findRider(riderId);

        for (int i=0; i<s.getResults().size(); i++) {
            if (s.getResults().get(i).getRider() == r) {
                throw new DuplicatedResultException("results already exist");
            }
        }

        if (checkpoints.length != s.getSegments().size()+2) {
            throw new InvalidCheckpointsException("invalid length");
        }

        if (s.getState() != StageState.STAGE_WAITING) {
            throw new InvalidStageStateException("invalid stage state");
        }

        Results res = new Results(r, s, checkpoints);
        s.addResults(res);
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);
        Rider r = findRider(riderId);
        for (Results res: s.getResults()) {
            if (r == res.getRider()) {
                return res.getTimes();
            }
        }
        return null;
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId)
            throws IDNotRecognisedException {
        Stage s = findStage(stageId);
        Rider r = findRider(riderId);

        Results riderResult = null;

        for (int i = 0; i < s.getResults().size(); i++) {
            if (r == s.getResults().get(i).getRider()) {
                return s.getResults().get(i).getAdjustedElapsedTime();
            }
        }

        return null;
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);
        Rider r = findRider(riderId);

        for (Results res: s.getResults()) {
            if (res.getRider() == r) {
                s.removeResults(res);
            }
        }
    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);
        int[] results = new int[s.getResults().size()];
        for (int i=0; i<s.getResults().size(); i++) {
            results[i] = s.getResults().get(i).getRider().getRiderId();
        }
        return results;
        // order the results
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        //same as above, just adjusted
        return null;
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);

        int[] ridersRanked = getRidersRankInStage(stageId);
        int[] riderPoints = new int[ridersRanked.length];

        for (int i=0; i<riderPoints.length; i++) {
            Rider r = findRider(ridersRanked[i]);
            riderPoints[i] = r.getPointsInStage(s,i+1);
        }
        return riderPoints;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage s = findStage(stageId);

        int[] ridersRanked = getRidersRankInStage(stageId);
        int[] riderPoints = new int[ridersRanked.length];

        for (int i=0; i<riderPoints.length; i++) {
            Rider r = findRider(ridersRanked[i]);
            riderPoints[i] = s.getClimbPoints(r);
        }

        return riderPoints;
    }

    @Override
    public void eraseCyclingPortal() {
        teams.clear();
        races.clear();
        Race.setDefaultId();
        Team.setDefaultId();
        Stage.setDefaultId();
        Segment.setDefaultId();
        Rider.setDefaultId();
    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        ObjectOutputStream ostream = new ObjectOutputStream(new FileOutputStream(filename));
        ostream.writeObject(this);
        ostream.close();
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream istream = new ObjectInputStream(new FileInputStream(filename));
        Object portalObject = istream.readObject();
        CyclingPortal portal = (CyclingPortal) portalObject;
        this.races = portal.races;
        this.teams = portal.teams;
        istream.close();
    }

    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        Race r = null;

        for (Race race: races) {
            if (Objects.equals(race.getName(), name)) {
                r = race;
            }
        }

        if (r == null) {
            throw new NameNotRecognisedException("race not found");
        }

        races.remove(r);
    }

    public ArrayList<Map.Entry<Rider, LocalTime>> getRiderTimes(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);

        ArrayList<Results> results = new ArrayList<>();
        for (Stage s: r.getStages()) {
            results.addAll(s.getResults());
        }

        Map<Rider, LocalTime> mapping = new HashMap<>();
        for (Results res: results) {
            Rider rider = res.getRider();
            if (mapping.containsKey(rider)) {
                long nanos = res.getAdjustedElapsedTime().toNanoOfDay();
                LocalTime newTime = mapping.get(rider).plusNanos(nanos);
                mapping.replace(rider, newTime);
            } else {
                mapping.put(rider, res.getAdjustedElapsedTime());
            }
        }

        ArrayList<Map.Entry<Rider, LocalTime>> allMap = new ArrayList<>(mapping.entrySet());
        allMap.sort(new AdjustTimeComparator());

        return allMap;
    }

    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);
        if (r.getResults().size() == 0) {
            return new int[0];
        }
        ArrayList<Map.Entry<Rider, LocalTime>> allMap = getRiderTimes(raceId);

        int[] riderIds = new int[allMap.size()];
        for (int i=0; i<riderIds.length; i++) {
            riderIds[i] = allMap.get(i).getKey().getRiderId();
        }

        return riderIds;
    }

    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);

        if (r.getResults().size() == 0) {
            return new LocalTime[0];
        }
        ArrayList<Map.Entry<Rider, LocalTime>> allMap = getRiderTimes(raceId);

        LocalTime[] orderedTimes = new LocalTime[allMap.size()];
        for (int i=0; i<orderedTimes.length; i++) {
            orderedTimes[i] = allMap.get(i).getValue();
        }

        return orderedTimes;
    }

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);

        if (r.getResults().size() == 0) {
            return new int[0];
        }
        ArrayList<Map.Entry<Rider, LocalTime>> allMap = getRiderTimes(raceId);

        int[] points = new int[allMap.size()];

        for (Stage s: r.getStages()) {
            int[] ranks = getRidersRankInStage(s.getStageId());

            int rank=0;

            for(int id: ranks) {
                ++rank;

                for(Map.Entry<Rider, LocalTime> mapVal: allMap) {
                    if (id == mapVal.getKey().getRiderId()) {
                        points[rank-1] += mapVal.getKey().getPointsInStage(s, rank);
                    }
                }
            }

        }

        return points;
    }

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        Race r = findRace(raceId);

        if (r.getResults().size() == 0) {
            return new int[0];
        }
        ArrayList<Map.Entry<Rider, LocalTime>> allMap = getRiderTimes(raceId);

        int[] points = new int[allMap.size()];

        for (Stage s: r.getStages()) {
            int index = 0;
            for(Map.Entry<Rider, LocalTime> mapVal: allMap) {
                points[index] += mapVal.getKey().getMountainPointsInStage(s);
                ++ index;
            }
        }

        return points;
    }

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        int[] ids = getRidersGeneralClassificationRank(raceId);
        int[] points = getRidersPointsInRace(raceId);

        Map<Rider, Integer> mapped = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            Rider r = findRider(ids[i]);
            mapped.put(r, points[i]);
        }

        ArrayList<Map.Entry<Rider, Integer>> allMap = new ArrayList<>(mapped.entrySet());

        allMap.sort(Comparator.comparing(Map.Entry<Rider, Integer>::getValue, (r1, r2) -> r2 - r1));

        int[] sortedIds = new int[ids.length];

        int index = 0;
        for (Map.Entry<Rider, Integer> mapVal: allMap) {
            sortedIds[index] = mapVal.getKey().getRiderId();
            ++index;
        }
        return sortedIds;
    }

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        int[] ids = getRidersGeneralClassificationRank(raceId);
        int[] points = getRidersMountainPointsInRace(raceId);

        Map<Rider, Integer> mapped = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            Rider r = findRider(ids[i]);
            mapped.put(r, points[i]);
        }

        ArrayList<Map.Entry<Rider, Integer>> allMap = new ArrayList<>(mapped.entrySet());

        allMap.sort(Comparator.comparing(Map.Entry<Rider, Integer>::getValue, (r1, r2) -> r2 - r1));

        int[] sortedIds = new int[ids.length];

        int index = 0;
        for (Map.Entry<Rider, Integer> mapVal: allMap) {
            sortedIds[index] = mapVal.getKey().getRiderId();
            ++index;
        }
        return sortedIds;
    }

}

