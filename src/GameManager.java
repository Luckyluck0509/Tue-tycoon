public class GameManager {
    Player player;

    double deltaTime = 1;

    public int quartile = 1;

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
}
