public class Player {
    public double knowledge = 0;
    public int creditProgress = 0;
    public double multiplier = 1;
    public double kpc = 1;
    public double kps = 0;
    public int numStudents = 0;
    public boolean inLecture = false;

    // calculate cost of hiring a new student
    public int getStudentPrice() {
        return (int)(20 * Math.pow(1.15, numStudents));
    }
}
