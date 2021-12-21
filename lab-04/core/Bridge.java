package core;

import utility.*;

public class Bridge extends Thing {
    private static String name = "Мост";

    public static class Side {

        public String whereToGo() {
            return "в сторону " + name + ".";
        }
    }

    public Bridge() {
        super("Мост");
        joinStory();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void joinStory() {
        System.out.println(this + " присоединился к истории.");
    }

    @Override
    public void doing() {
        System.out.println(this + " так делал.");
    }

    @Override
    public void coming() {
        System.out.println(this + " появился.");
    }

    @Override
    public void coming(Thing place) {
        System.out.println(this + " появился из-под " + place.toString() + ".");
    }

    @Override
    public String toString() {
        return "Мост '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Bridge) {
            return name.equals(((Bridge) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
