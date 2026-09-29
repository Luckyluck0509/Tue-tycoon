public class Player {
    double DeltaTime = 0.0167;

    public double knowledge = 0;
    public int creditProgress = 0;
    public double multiplier = 1;
    public double kpc = 0;
    public double kps = 0;
    public int numStudents = 0;
    public int rebirthNum = 0;

    public boolean[][] courses = new boolean[3][4];

    public void IncreaseKnowledge() {
        this.knowledge += (this.kpc + this.kps) * this.multiplier;
    }

    public void IncreaseMultiplier() {
        this.multiplier += 0.1 * rebirthNum;
    }

    public void IncreaseStudentNum() {
        this.numStudents += 1;
    }

    public double UpdateKps() {
        return kps + (kpc * DeltaTime);
    }

    public void FinishCourse(double cost, int courseNum) {
        this.knowledge -= cost;
        creditProgress += 5;
        this.courses[rebirthNum + 1][courseNum] = true;
    }
}
