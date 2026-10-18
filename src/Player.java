public class Player {
    // knowledge related variables
    public double totalKnowledge;
    public double multiplier = 1;
    public double knowledge = 0;
    public double kpc = 1;

    // quartile related variables
    public int creditProgress = 0;
    public int quartile = 1;

    // students related variables
    public int studentsHired;
    public int numStudents = 0;
    public double kps = 0;

    // lecture related variables
    public int lecturesAttended;
    public boolean inLecture = false;
    public double lectureTime = 0;
    public int lectureLength = 0;

    // course related variables
    public boolean[][] courses = new boolean[4][3];
    public  int courseNumber = 1;
    public int courseRequirement = 0;

    // time related variables
    public double time = 3600;
    public double deltaTime;
    public int seconds;
    public int minutes;
    public double sleepTime;
}
