package OOPs;

public class Circle extends ShapesAbs {
    double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    String getInfo() {
        return "this is a circle of colour " + super.color + " and Area " + getArea();
    }
}
