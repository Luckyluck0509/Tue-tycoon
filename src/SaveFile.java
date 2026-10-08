import java.io.*;
import java.nio.file.*;
import java.util.Properties;


public class SaveFile {
    public final Path SAVE_PATH = Path.of("save.txt");

    public void save(Player player, GameManager gameManager, MainScreen mainScreen) throws IOException {
        Properties prop = new Properties();

        // Player
        prop.setProperty("knowledge", String.valueOf(player.knowledge));
        prop.setProperty("creditProgress", String.valueOf(player.creditProgress));
        prop.setProperty("multiplier", String.valueOf(player.multiplier));
        prop.setProperty("kpc", String.valueOf(player.kpc));
        prop.setProperty("kps", String.valueOf(player.kps));
        prop.setProperty("numStudents", String.valueOf(player.numStudents));
        prop.setProperty("inLecture", String.valueOf(player.inLecture));

        // MainScreen
        prop.setProperty("time", String.valueOf(mainScreen.time));

        // GameManager
        prop.setProperty("quartile", String.valueOf(gameManager.quartile));
        prop.setProperty("lectureTime", String.valueOf(gameManager.lectureTime));
        prop.setProperty("courseNumber", String.valueOf(gameManager.courseNumber));
        prop.setProperty("courseRequirement", String.valueOf(gameManager.courseRequirement));

        try (var out = Files.newOutputStream(SAVE_PATH)) {
            prop.store(out, "Save file");
        }
    }

    public void load(Player player, GameManager gameManager, MainScreen mainScreen) throws IOException {
        Properties prop = new Properties();

        if (!Files.exists(SAVE_PATH)) {
            return;
        }

        try (var in = Files.newInputStream(SAVE_PATH)) {
            prop.load(in);
        }

        // Player
        player.knowledge = Double.parseDouble(prop.getProperty("knowledge", "0"));
        player.creditProgress = Integer.parseInt(prop.getProperty("creditProgress", "0"));
        player.multiplier = Double.parseDouble(prop.getProperty("multiplier", "1"));
        player.kpc = Double.parseDouble(prop.getProperty("kpc", "1"));
        player.kps = Double.parseDouble(prop.getProperty("kps", "0"));
        player.numStudents = Integer.parseInt(prop.getProperty("numStudents", "0"));
        player.inLecture = Boolean.parseBoolean(prop.getProperty("inLecture", "false"));

        // MainScreen
        mainScreen.time = Double.parseDouble(prop.getProperty("time", "3600"));

        // GameManager
        gameManager.quartile = Integer.parseInt(prop.getProperty("quartile", "1"));
        gameManager.lectureTime = Double.parseDouble(prop.getProperty("lectureTime", "0"));
        gameManager.courseNumber = Integer.parseInt(prop.getProperty("courseNumber", "1"));
        gameManager.courseRequirement = Integer.parseInt(prop.getProperty("courseRequirement", "0"));
    }
}
