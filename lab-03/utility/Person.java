package utility;

public abstract class Person extends Thing {
    public Person(final String name) {
        super(name);
    }

    public abstract void doing();
}