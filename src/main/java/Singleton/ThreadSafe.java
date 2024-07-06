package Singleton;

public class ThreadSafe {
    private static ThreadSafe tSafe;

    private ThreadSafe() {

    }

    public static synchronized ThreadSafe getInstance() {
        if (tSafe == null) {
            tSafe = new ThreadSafe();
        }
        return tSafe;
    }
}
