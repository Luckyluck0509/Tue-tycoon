public class Generator {
    public double kps;
    public double price;

    public Generator(int price) {
        this.price = price;
        this.kps = 0.08 * Math.pow(price, 0.80);

    }

}
