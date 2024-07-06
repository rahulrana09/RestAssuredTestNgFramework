package OOPs;

interface ShapesInt {
    String color();

    double area();

    Void info();

    default void defaultMessage(){
        System.out.println("Default message");
    }
    public static void staticMethod(){
        System.out.println("Static Method");
    }
}
