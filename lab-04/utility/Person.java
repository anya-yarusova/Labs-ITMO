package utility;

public abstract class Person extends Thing {
    public Person(final String name) {
        super(name);
    }

    public abstract void saying(String message);

    public abstract void going();

    public abstract void seeing(SlideAble slide);

    public abstract void thinking();
}