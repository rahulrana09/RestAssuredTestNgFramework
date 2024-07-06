package OOPs;

import org.junit.Test;

@FunctionalInterface
interface Action {
    // can only have one not overriding abstract method
    void show(String data);

//    below toString() is allowed, as its a method implemented on Object Class
//    String toString();
}

//class ActionImpl implements Action {
//    public void show() {
//        System.out.println("What the hell");
//    }
//}
// this implementation could be done in runs() by creating an anonymous Inner Class

@FunctionalInterface
interface ActionOne {
    int add(int i, int j);
}


public class Functional_Int {
    @Test
    public void runs() {
//        ActionImpl obj = new ActionImpl();
//        obj.show();
        // imp with ActionImpl

//        Action obj = new Action() {
//            public void show() {
//                System.out.println("Using Anonymous inner class");
//            }
//        };
//        obj.show();

        Action obj1 = (String data) -> System.out.println("Using Anonymous inner class, using lambda, " + data);
        obj1.show("WAO");
    }

    @Test
    public void run1() {
        ActionOne obj = (i, j) -> i + j;
        System.out.println("Add two number; " + obj.add(1, 2));
    }

}
