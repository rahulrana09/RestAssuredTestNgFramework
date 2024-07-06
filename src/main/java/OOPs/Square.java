package OOPs;

public class Square implements ShapesInt {
    double side;
    String color;

    public Square(String color, double side) {
        this.side = side;
        this.color = color;
    }

    @Override
    public String color() {
        return this.color;
    }

    @Override
    public double area() {
        return Math.pow(side, 2);
    }

    @Override
    public Void info() {
        System.out.println("I am Square with Color: " + color + " and area:" + area());
        return null;
    }
}
