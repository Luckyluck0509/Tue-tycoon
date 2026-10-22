public class Rebirth {
    // calculate the amount the multiplier is gonna increase after rebirth
    public double multiplierAmount(Player player) {
        double quartile = player.quartile;
        double knowledge = player.knowledge;
        double kpc = player.kpc;
        double creditsThisQuartile = (player.courseNumber - 1) * 5;
        double p = Math.min(creditsThisQuartile / 15, 1);

        if (quartile > 1) {
            return 0.10 * Math.log10(1 + Math.log10(1 + knowledge))
                    * Math.sqrt(kpc) * (0.6 + 0.6 * p) * 1.15 * (quartile - 1);
        } else {
            return 0.10 * Math.log10(1 + Math.log10(1 + knowledge))
                    * Math.sqrt(kpc) * (0.6 + 0.6 * p);
        }
    }

    // rebirth, update multiplier and trigger reset at mode 0
    public void rebirthAction(Player player) {
        double delta = multiplierAmount(player);
        player.multiplier += delta;

        reset(player, 0);
    }

    // resetting variables
    public void reset(Player player, int mode) {
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
            player.bsaTriggered = false;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    player.courses[i][j] = false;
                }
            }
        }
    }
}
