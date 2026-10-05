import java.io.*;
import java.util.Properties;
import java.nio.file.*;

public class SaveFile {
    private static final Path savePath = Path.of("save.txt");

    public void save(Player player, GameManager gameManager) throws IOException {
        Properties prop = new Properties();

        // Player
        prop.setProperty("knowledge", String.valueOf(player.knowledge));
        prop.setProperty("creditProgress", String.valueOf(player.creditProgress));
        prop.setProperty("multiplier", String.valueOf(player.multiplier));
        prop.setProperty("kpc", String.valueOf(player.kpc));
        prop.setProperty("kps", String.valueOf(player.kps));
        prop.setProperty("numStudents", String.valueOf(player.numStudents));
        prop.setProperty("inLecture", String.valueOf(player.inLecture));

        // GameManager
        prop.setProperty("time", String.valueOf(gameManager.time));
        prop.setProperty("quartile", String.valueOf(gameManager.quartile));
        prop.setProperty("lectureTime", String.valueOf(gameManager.lectureTime));

        try (var out = Files.newOutputStream(savePath)) {
            prop.store(out, "Save file");
        }
    }

    public void load(Player player, GameManager gameManager) throws IOException {
        Properties prop = new Properties();

        if (!Files.exists(savePath)) {
            return;
        }

        try (var in = Files.newInputStream(savePath)) {
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

        // GameManager
        gameManager.time = Double.parseDouble(prop.getProperty("time", "3600"));
        gameManager.quartile = Integer.parseInt(prop.getProperty("quartile", "1"));
        gameManager.lectureTime = Double.parseDouble(prop.getProperty("lectureTime", "0"));
    }



}
