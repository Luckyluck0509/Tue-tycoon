public class Student {
    public double kps;
    public double price;

    // constructor, calculate kps gained based on price, subtract knowledge
    public Student(int price, Player player) {
        this.price = price;
        this.kps = 0.08 * Math.pow(price, 0.75);
        player.knowledge -= price;
    }
}
