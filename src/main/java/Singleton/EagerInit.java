package Singleton;

public class EagerInit {
    private static EagerInit instance = new EagerInit();

    private EagerInit() {

    }

    public EagerInit getInstance() {
        return instance;
    }
}
