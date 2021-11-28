package solution.query.actors;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.Database;
import solution.commands.Rating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Average {

    private static Average averageQuery = null;
    public static Average getAverageQuery() {
        if (averageQuery == null) {
            averageQuery = new Average();
        }
        return averageQuery;
    }

    public List<String> createBestActorsList(final ActionInputData command,
                                             Database data) {

        List<String> bestActors = new ArrayList<>();
        int number = command.getNumber();
        class IntPairHelper {
            public IntPairHelper(double grade, String name) {
                this.grade = grade;
                this.name = name;
            }

            double grade;
            String name;
        }
        List<IntPairHelper> actorsRatings = new ArrayList<>();
        for (ActorInputData actor : data.getActors()) {
            List<String> videoList = actor.getFilmography();
            double grade = 0.0;
            int count = 0;
            for (String title : videoList) {
                if (Rating.getRating().getRatingByTitle(title, data) != 0) {
                    grade += Rating.getRating().getRatingByTitle(title, data);
                    count++;
                }

            }
            if (grade != 0.0 && count != 0) {
                grade = grade / count;
                actorsRatings.add(new IntPairHelper(grade, actor.getName()));
            }
        }
        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(actorsRatings, (o1, o2) -> {
                if (Double.compare(o1.grade, o2.grade) < 0) {
                    return -1;
                } else if (Double.compare(o1.grade, o2.grade) > 0) {
                    return 1;
                } else return o1.name.compareToIgnoreCase(o2.name);

            });
        } else {
            Collections.sort(actorsRatings, (o1, o2) -> {
                if (Double.compare(o1.grade, o2.grade) < 0) {
                    return 1;
                } else if (Double.compare(o1.grade, o2.grade) > 0) {
                    return -1;
                } else return o2.name.compareToIgnoreCase(o1.name);

            });
        }

        for (int i = 0; i < number; i++) {
            if (actorsRatings.size() <= i) {
                break;
            }
            bestActors.add(actorsRatings.get(i).name);
        }
        return bestActors;
    }




    public void averageQuery(final ActionInputData command, Database data,
                             final Writer fileWriter,
                             final JSONArray arrayResult) throws IOException {

        List<String> bestActors = createBestActorsList(command, data);
        String outputMessage = "Query result: " + bestActors;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
