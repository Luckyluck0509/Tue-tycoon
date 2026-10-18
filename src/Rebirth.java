public class Rebirth {
    // calculate the amount the multiplier is gonna increase after rebirth
    public double MultiplierAmount(GameManager gameManager, Player player){
        double q = player.quartile;
        double c = player.creditProgress;
        double k = player.knowledge;
        double kpc = player.kpc;
        double creditsThisQuartile = (player.courseNumber - 1) * 5;
        double p = Math.min(creditsThisQuartile / 15, 1);

        return 0.10 * Math.log10(1 + k) * Math.sqrt(kpc) * (0.6 + 0.6 * p) * (1 + 0.15 * (q - 1));
    }

    // rebirth, update multiplier and trigger reset at mode 0
    public void RebirthAction(GameManager gameManager, Player player) {
        double delta = MultiplierAmount(gameManager, player);
        player.multiplier += delta;

        reset(gameManager, player, 0);
    }

    // resetting variables
    public void reset(GameManager gameManager, Player player, int mode) {
        // mode 0 = rebirth reset
        // mode 1 = complete reset

        player.knowledge = 0;
        player.kps = 0;
        player.kpc = 1;
        player.numStudents = 0;
        player.inLecture = false;

        player.lectureTime = 0;
        player.lectureLength = 0;
        player.courseNumber = 1;

        if (mode == 0) {
            player.quartile++;
        } else {
            player.studentsHired = 0;
            player.lecturesAttended = 0;
            player.totalKnowledge = 0;
            player.creditProgress = 0;
            player.multiplier = 1;
            player.quartile = 1;
            player.time = 3600;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    player.courses[i][j] = false;
                }
            }
        }


    }
}
