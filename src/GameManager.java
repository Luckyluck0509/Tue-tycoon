public class GameManager {
    Player player;

    double deltaTime = 1;

    public double lectureTime = 0;
    public double time = 3600;

    public int quartile = 1;
    public  int courseNumber = 1;
    public int courseRequirement = 0;
    public boolean[][] courses = new boolean[4][3];


    public GameManager(Player p) {
        this.player = p;
    }

    public void studyButtonPressed() {
        player.knowledge += player.kpc * player.multiplier;
    }

    public void goToLectureButtonPressed() {
        player.inLecture = true;
        player.kpc += 1;
        lectureTime = Math.pow(player.kpc, 1.2);
    }

    public void addStudentButtonPressed() {
        Student student = new Student(player.getStudentPrice(), player);
        player.numStudents += 1;
        player.kps += (student.kps * player.multiplier);
    }

    public void finishCourseButtonPressed() {
        this.courses[quartile][this.courseNumber - 1] = true;
        this.courseNumber += 1;
    }

    public void setCourseRequirement() {
        this.courseRequirement = 1000 * (2 * this.courseNumber) * (10 * this.quartile);
    }
}
