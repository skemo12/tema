package solution.recommendation.premium;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.Show;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.recommendation.RecommendationString;
import solution.utility.Utility;
import solution.recommendation.standard.StandardRecommendation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for favorite recommendation, used to get favorite recommendation
 */
public final class FavoriteRecommendation implements RecommendationString {

    /**
     * Make it Singleton
     */
    private static FavoriteRecommendation favoriteRecommendation = null;
    /**
     * Singleton function
     */
    public static FavoriteRecommendation getInstance() {
        if (favoriteRecommendation == null) {
            favoriteRecommendation = new FavoriteRecommendation();
        }
        return favoriteRecommendation;
    }

    /**
     * Sorts list of shows by favorite count, and if favorite count is equals
     * sorts according to order in database
     */
    private List<Show> sortShowsByFavoriteCount(final List<Show> videos,
                                         final Database data) {

        Collections.sort(videos, (o1, o2) -> {
            if (o1.getFavoriteCount() > o2.getFavoriteCount()) {
                return -1;
            } else if (o1.getFavoriteCount() < o2.getFavoriteCount()) {
                return 1;
            } else {
                return Utility.getInstance().getDatabaseOrder(o1, o2, data);
            }
        });
        return videos;
    }

    /**
     * Searches and returns String video result
     */
    public String searchVideo(final ActionInputData command,
                              final Database data) {

        Utility.getInstance().updateFavoriteCount(data);
        List<Show> videos = new ArrayList<>();
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
                command.getUsername());

        for (Movie movie : data.getMovies()) {
            if (movie.getFavoriteCount() != 0.0) {
                videos.add(movie);
            }
        }

        for (Serial serial : data.getSerials()) {
            if (serial.getFavoriteCount() != 0.0) {
                videos.add(serial);
            }
        }

        videos = sortShowsByFavoriteCount(videos, data);
        for (Show video : videos) {
            if (!user.getHistory().containsKey(video.getTitle())) {
                return video.getTitle();
            }
        }

        return StandardRecommendation.getInstance().searchVideo(command, data);
    }

    /**
     * Creates output message and calls method for recommendation
     */
    public void getRecommendation(final ActionInputData action,
                                  final Database data) throws IOException {

        if (!CheckPremium.getInstance().checkPremium(action, data)) {
            String outputMessage = "FavoriteRecommendation cannot be applied!";
            Utility.getInstance().writeOutputMessage(data, action,
                    outputMessage);
            return;
        }

        String video = searchVideo(action, data);

        if (video == null) {
            String commandType = action.getType();
            commandType = commandType.substring(0, 1).toUpperCase()
                    + commandType.substring(1).toLowerCase();
            String outputMessage =  commandType
                    + "Recommendation cannot be applied!";
            Utility.getInstance().writeOutputMessage(data, action,
                    outputMessage);
            return;
        }

        String outputMessage = "FavoriteRecommendation result: " + video;
        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);

    }
}
