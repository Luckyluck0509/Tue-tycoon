public class Rebirth {
    public void Rebirth(int noRebirth, GameManager gameManager, Player player) {
        double q = gameManager.quartile;
        double c = player.creditProgress;
        double k = player.knowledge;
        double kpc = player.kpc;
        double p = Math.min((4*c)/ q, 1.5);

        double delta = 0.05 * Math.log(1 + k) * Math.sqrt(kpc) * (0.5 + p) * (1 + 0.25 * (q - 1));
        player.multiplier += delta;

        reset(gameManager, player, 0);
    }

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
            gameManager.quartile += 1;
        } else
            player.creditProgress = 0;
            gameManager.quartile = 1;
            gameManager.time = 3600;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    player.courses[i][j] = false;
                }
            }



    }
}
