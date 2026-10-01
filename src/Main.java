void main() {
    Player p = new Player();
    GameManager gm = new GameManager(p);

    int time = 3600;
//    Renderer r = new Renderer(p, m); // Display p. On events, call method of m.

    while (time > 0) {
        gm.update();
        time --;
    }


}
