public class Student {
    public double kps;
    public double price;

    public Student(int price, Player player) {
        this.price = price;
        this.kps = 0.08 * Math.pow(price, 0.75);
        System.out.println(this.kps);
        player.knowledge -= price;
    }
}
