public class GameManager {
    Player player;

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
        player.knowledge += player.kpc * player.multiplier;
    }

    // going to lecture, updates kpc and freezes study action
    public void goToLectureButtonPressed() {
        player.inLecture = true;
        player.kpc *= 1.5;
        lectureTime = 4 * Math.pow(player.kpc, 1.35);
        lectureLength = (int) lectureTime;
    }

    // hires a student, increases kps
    public void addStudentButtonPressed() {
        Student student = new Student(player.getStudentPrice(), player);
        player.numStudents += 1;
        player.kps += student.kps;
    }

    // sets course to finished, adds ects, add multiplier
    public void finishCourseButtonPressed() {
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

    // calculate cost of finishing course
    public void setCourseRequirement() {
        this.courseRequirement = (int)(15000 * courseNumber * Math.pow(2.3, quartile - 1));
    }
}
