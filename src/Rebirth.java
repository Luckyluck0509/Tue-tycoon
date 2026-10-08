public class Rebirth {
    // calculate the amount the multiplier is gonna increase after rebirth
    public double MultiplierAmount(GameManager gameManager, Player player){
        double q = gameManager.quartile;
        double c = player.creditProgress;
        double k = player.knowledge;
        double kpc = player.kpc;
        double p = Math.min((4*c)/ q, 1.5);

        return 0.05 * Math.log(1 + k) * Math.sqrt(kpc) * (0.5 + p) * (1 + 0.25 * (q - 1));
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

        gameManager.lectureTime = 0;

        if (mode == 0) {
            gameManager.quartile++;
            gameManager.courseNumber = 1;
        } else {
            player.creditProgress = 0;
            player.multiplier = 1;
            gameManager.quartile = 1;
            gameManager.time = 3600;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    gameManager.courses[i][j] = false;
                }
            }
        }


    }
}
