package Singleton;

public class DoubleCheck {
    private static volatile DoubleCheck instance;
    private int count = 0;

    private DoubleCheck() {
    }

    public static DoubleCheck getInstance() {
        if (instance == null) {
            synchronized (DoubleCheck.class) {
                if (instance == null) {
                    instance = new DoubleCheck();
                }
            }
        }

        return instance;
    }

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
