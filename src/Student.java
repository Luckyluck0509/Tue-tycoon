public class Student {
    public double kps;
    public double price;

    // constructor, calculate kps gained based on price, subtract knowledge
    public Student(double price, Player player) {
        this.price = price;
        kps = 0.388 * Math.pow(price, 0.38);
        player.knowledge -= price;
    }
}
