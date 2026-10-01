public class Player {
    GameManager gameManager = new GameManager(this);

    public double knowledge = 0;
    public int creditProgress = 0;
    public double multiplier = 1;
    public double kpc = 1;
    public double kps = 0;
    public int numStudents = 0;

    public boolean[][] courses = new boolean[3][4];

    public void IncreaseMultiplier() {
        this.multiplier += 0.1 * (gameManager.quartile - 1);
    }

    public void IncreaseStudentNum() {
        this.numStudents += 1;
    }

    public double UpdateKps() {
        return kps + (kpc * gameManager.deltaTime);
    }
}
