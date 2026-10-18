import java.io.*;
import java.nio.file.*;
import java.util.Properties;


public class SaveFile {
    // find the filepath of the savefile
    public final Path SAVE_PATH = Path.of("save.txt");

    // save all variables to the save file
    public void save(Player player, GameManager gameManager, MainScreen mainScreen) throws IOException {
        Properties prop = new Properties();

        // save variables
        prop.setProperty("knowledge", String.valueOf(player.knowledge));
        prop.setProperty("totalKnowledge", String.valueOf(player.totalKnowledge));
        prop.setProperty("creditProgress", String.valueOf(player.creditProgress));
        prop.setProperty("multiplier", String.valueOf(player.multiplier));
        prop.setProperty("kpc", String.valueOf(player.kpc));
        prop.setProperty("kps", String.valueOf(player.kps));
        prop.setProperty("numStudents", String.valueOf(player.numStudents));
        prop.setProperty("studentsHired", String.valueOf(player.studentsHired));
        prop.setProperty("inLecture", String.valueOf(player.inLecture));
        prop.setProperty("lecturesAttended", String.valueOf(player.lecturesAttended));
        prop.setProperty("quartile", String.valueOf(player.quartile));
        prop.setProperty("lectureTime", String.valueOf(player.lectureTime));
        prop.setProperty("lectureLength", String.valueOf(player.lectureLength));
        prop.setProperty("courseNumber", String.valueOf(player.courseNumber));
        prop.setProperty("courseRequirement", String.valueOf(player.courseRequirement));
        prop.setProperty("time", String.valueOf(player.time));

        // store 2D courses array as binary
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i > 0) {
                sb.append(",");
            }
            for (boolean value : player.courses[i]) {
                sb.append(value ? '1' : '0');
            }
        }

        prop.setProperty("courses", sb.toString());

        try (var out = Files.newOutputStream(SAVE_PATH)) {
            prop.store(out, "Save file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load all variables from the save file and assign them
    public void load(Player player, GameManager gameManager) throws IOException {
        Properties prop = new Properties();

        if (!Files.exists(SAVE_PATH)) {
            return;
        }

        try (var in = Files.newInputStream(SAVE_PATH)) {
            prop.load(in);
        }

        // load variables
        player.knowledge = Double.parseDouble(prop.getProperty("knowledge", "0"));
        player.totalKnowledge = Double.parseDouble(prop.getProperty("totalKnowledge", "0"));
        player.creditProgress = Integer.parseInt(prop.getProperty("creditProgress", "0"));
        player.multiplier = Double.parseDouble(prop.getProperty("multiplier", "1"));
        player.kpc = Double.parseDouble(prop.getProperty("kpc", "1"));
        player.kps = Double.parseDouble(prop.getProperty("kps", "0"));
        player.numStudents = Integer.parseInt(prop.getProperty("numStudents", "0"));
        player.studentsHired = Integer.parseInt(prop.getProperty("studentsHired", "0"));
        player.inLecture = Boolean.parseBoolean(prop.getProperty("inLecture", "false"));
        player.lecturesAttended = Integer.parseInt(prop.getProperty("lecturesAttended", "0"));
        player.quartile = Integer.parseInt(prop.getProperty("quartile", "1"));
        player.lectureTime = Double.parseDouble(prop.getProperty("lectureTime", "0"));
        player.lectureLength = Integer.parseInt(prop.getProperty("lectureLength", "0"));
        player.courseNumber = Integer.parseInt(prop.getProperty("courseNumber", "1"));
        player.courseRequirement = Integer.parseInt(prop.getProperty("courseRequirement", "0"));
        player.time = Double.parseDouble(prop.getProperty("time", "3600"));

        String savedCourses = prop.getProperty("courses");
        if (savedCourses != null) {
            String[] rows = savedCourses.split(",");
            boolean[][] courses = new boolean[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                courses[i] = new boolean[rows[i].length()];
                for (int j = 0; j < rows[i].length(); j++) {
                    courses[i][j] = rows[i].charAt(j) == '1';
                }
            }
            player.courses = courses;
        }
    }
}
