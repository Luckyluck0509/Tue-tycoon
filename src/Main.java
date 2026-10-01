void main() {
    Player p = new Player();
    GameManager gm = new GameManager(p);
    MainScreen ms = new MainScreen(p, gm);
}
