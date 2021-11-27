package solution;

import entertainment.Season;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Show{
    /**
     * Number of seasons
     */
    private int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Season> seasons;
    private List<Double> gradePerSeason;
    private List<List<UserInputData>> ratingUsers;

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public List<Double> getGradePerSeason() {
        return gradePerSeason;
    }

    public void setGradePerSeason(List<Double> gradePerSeason) {
        this.gradePerSeason = gradePerSeason;
    }

    public List<List<UserInputData>> getRatingUsers() {
        return ratingUsers;
    }

    public void setRatingUsers(List<List<UserInputData>> ratingUsers) {
        this.ratingUsers = ratingUsers;
    }

    public Serial(String title, ArrayList<String> cast,
                  ArrayList<String> genres, int numberOfSeasons,
                  ArrayList<Season> seasons, int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.gradePerSeason = new ArrayList<>(numberOfSeasons);
        for (int i = 0; i < numberOfSeasons; i++) {
            this.gradePerSeason.add(0.0);
        }
        this.ratingUsers = new ArrayList<>(numberOfSeasons);
        for (int i = 0; i < numberOfSeasons; i++) {
            this.ratingUsers.add(new ArrayList<>());
        }

    }

    public Serial(SerialInputData serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(),
                serial.getGenres());
        this.numberOfSeasons = serial.getNumberSeason();
        this.seasons = serial.getSeasons();
        this.gradePerSeason = new ArrayList<>(numberOfSeasons);
        for (int i = 0; i < numberOfSeasons; i++) {
            this.gradePerSeason.add(0.0);
        }
        this.ratingUsers = new ArrayList<>(numberOfSeasons);
        for (int i = 0; i < numberOfSeasons; i++) {
            this.ratingUsers.add(new ArrayList<>());
        }
    }
}