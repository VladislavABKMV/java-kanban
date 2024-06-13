package service;

public class Managers {
    public InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
