import java.util.Random;

public class GameManager {
    Player player;
    Random rand = new Random();

    // all sounds
    Sound studySound1 = new Sound("/Resources/Sounds/studySound1.wav");
    Sound studySound2 = new Sound("/Resources/Sounds/studySound2.wav");
    Sound upgradeSound = new Sound("/Resources/Sounds/upgradeSound.wav");
    Sound courseSound = new Sound("/Resources/Sounds/courseSound.wav");
    Sound rebirthSound = new Sound("/Resources/Sounds/rebirthSound.wav");
    Sound popupSound = new Sound("/Resources/Sounds/popupSound.wav");
    Sound exitSound = new Sound("/Resources/Sounds/exitSound.wav");
    Sound mutedSound = new Sound("/Resources/Sounds/mutedSound.wav");
    Sound resetSound = new Sound("/Resources/Sounds/resetSound.wav");
    Sound failSound = new Sound("/Resources/Sounds/failSound.wav");

    double deltaTime = 1;

    public double lectureTime = 0;
    public int lectureLength = 0;
    public double time = 3600;

    public int quartile = 1;
    public  int courseNumber = 1;
    public int courseRequirement = 0;
    public boolean[][] courses = new boolean[4][3];


    // constructor
    public GameManager(Player p) {
        this.player = p;
    }

    // updates player knowledge when study button is pressed
    public void studyButtonPressed() {
        int num = rand.nextInt(4);

        switch (num) {
            case 1 -> {
                studySound2.playSound();
            } default -> {
                studySound1.playSound();
            }
        }

        player.knowledge += player.kpc * player.multiplier;
        player.totalKnowledge += player.kpc * player.multiplier;
    }

    // going to lecture, updates kpc and freezes study action
    public void goToLectureButtonPressed() {
        if (!player.inLecture) {
            upgradeSound.playSound();
            player.inLecture = true;
            lectureTime = 4 * Math.pow(player.kpc, 1.35);
            lectureLength = (int) lectureTime;
            player.lecturesAttended ++;
        }
    }

    // hires a student, increases kps
    public void addStudentButtonPressed() {
        if (player.knowledge >= player.getStudentPrice()) {
            upgradeSound.playSound();
            Student student = new Student(player.getStudentPrice(), player);
            player.numStudents += 1;
            player.kps += student.kps;
            player.studentsHired ++;
        }
    }

    // sets course to finished, adds ects, add multiplier
    public void finishCourseButtonPressed() {
        if (player.knowledge >= courseRequirement && courseNumber <= 3) {
            courseSound.playSound();
            this.courses[quartile - 1][this.courseNumber - 1] = true;
            this.courseNumber += 1;
            player.creditProgress += 5;

            if (this.courseNumber <= 3) {
                player.multiplier += 0.10;
            } else {
                player.multiplier += 0.20;
            }
            player.knowledge -= this.courseRequirement;
        }
    }

    // calculate cost of finishing course
    public void setCourseRequirement() {
        this.courseRequirement = (int)(15000 * courseNumber * Math.pow(2.3, quartile - 1));
    }
}
