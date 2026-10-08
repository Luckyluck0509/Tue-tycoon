public class Player {
    GameManager gameManager = new GameManager(this);

    public double knowledge = 0;
    public int creditProgress = 0;
    public double multiplier = 1;
    public double kpc = 1;
    public double kps = 0;
    public int numStudents = 0;
    public boolean inLecture = false;

    public int getStudentPrice() {
        return (int) (4 * Math.pow(this.numStudents, 3) + 25);
    }
}
