package OOPs;

abstract class ShapesAbs {
    String color;

    public ShapesAbs(String color) {
        this.color = color;
    }

    abstract double getArea();

    abstract String getInfo();

    public String getColor() {
        return this.color;
    }
}
