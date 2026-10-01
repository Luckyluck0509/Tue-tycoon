public class Student {
    public double kps;
    public double price;

    public Student(int price) {
        this.price = price;
        this.kps = 0.08 * Math.pow(price, 0.80);
        Generator();
    }

    public void Generator() {
        Player player = new Player();
        player.kps += this.kps;
        player.IncreaseStudentNum();
    }
}
