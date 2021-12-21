package core;

import utility.*;

public class Cone extends Thing implements FloatAble {
    private Size type = Size.SMALL;

    public Cone() {
        super("Шишка");
        joinStory();
    }

    public Cone(final String name) {
        super(name);
        joinStory();
    }

    public Cone(Size type) {
        super("Шишка");
        this.type = type;
        joinStory();
    }

    public Cone(final String name, Size type) {
        super(name);
        this.type = type;
        joinStory();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Size getSize() {
        return type;
    }

    @Override
    public void joinStory() {
        System.out.println("Шишка '" + name + "' присоединилась к истории.");
    }

    @Override
    public String toString() {
        return "Шишка '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Cone) {
            return (name.equals(((Cone) obj).getName()) && type.equals(((Cone) obj).getSize()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public void surface() {
        System.out.println("Шишка '" + name + "' всплыла.");
    }

    @Override
    public void compare(FloatAble coneAnother) {
        if (this.equals(coneAnother))
            System.out.println("Шишки одинакового размера.");
        else
            System.out.println("Шишки разного размера.");
    }

    @Override
    public void surfaceOrdered(FloatAble coneAnother) {
        if (this.getSize() == Size.BIG) {
            this.surface();
            coneAnother.surface();
        } else {
            coneAnother.surface();
            this.surface();
        }
    }
}