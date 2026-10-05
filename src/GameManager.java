public class GameManager {
    Player player;

    double deltaTime = 1;

    public int quartile = 1;
    public double lectureTime = 0;
    public double time = 3600;

    public GameManager(Player p) {
        this.player = p;
    }

    public void StudyButtonPressed() {
        player.knowledge += player.kpc * player.multiplier;
    }

    public void AddStudentButtonPressed() {
        Student student = new Student(player.getStudentPrice(), player);
        player.numStudents += 1;
        player.kps += student.kps;
    }

    public void GoToLectureButtonPressed() {
        player.inLecture = true;
        player.kpc += 1;
        lectureTime = Math.pow(player.kpc, 2);
    }
}
