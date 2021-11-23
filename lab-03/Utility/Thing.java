package Utility;

public abstract class Thing {
    protected final String name;

    public abstract String getName();

    public abstract void joinStory();

    public Thing(final String name) {
        this.name = name;
    }
}