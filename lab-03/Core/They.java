package Core;

import Utility.*;

public class They extends Person implements PersonInterface {
    private boolean winner = false;
    private boolean known = true;
    Size planningCone = Size.SMALL;

    public They() {
        super("Персонажи");
        joinStory();
    }

    public They(final String name) {
        super(name);
        joinStory();
    }

    @Override
    public boolean getWinner() {
        return winner;
    }

    @Override
    public boolean getKnown() {
        return known;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void joinStory() {
        System.out.println("Персонажи '" + name + "' присоединились к истории.");
    }

    @Override
    public String toString() {
        return "Персонажи '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Person) {
            return name.equals(((Person) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public void doing() {
        System.out.println("Персонажи '" + name + "' делают что-то.");
    }

    @Override
    public void waiting() {
        System.out.println("Персонажи '" + name + "' ждут, какая из шишек выплывет  первой.");
    }

    @Override
    public void throwing(ConeInterface coneFirst, ConeInterface coneSecond) {
        if (coneFirst.getSize().equals(coneSecond.getSize())) known = false;
        if (known && planningCone == Size.BIG) winner = true;
        System.out.println("Персонажи '" + name + "' бросили " + "шишку " + coneFirst.getSize().toString() + " и шишку " + coneSecond.getSize().toString() + ".");
    }

    @Override
    public void knowing() {
        if (known) System.out.println("Персонажи '" + name + "' знают, какая шишка выплыла первой.");
        else System.out.println("Персонажи '" + name + "' не знают, какая шишка выплыла первой.");
    }

    @Override
    public void planning(ConeInterface cone) {
        System.out.println("Персонажи '" + name + "' задумали, какая шишка выплывет первой.");
        planningCone = cone.getSize();
    }

    @Override
    public void winning() {
        if (winner) System.out.println("Персонажи '" + name + "' выиграли 2 раза.");
        else System.out.println("Персонажи '" + name + "' проиграли.");
    }

    @Override
    public void doingDefault() {
        winner = false;
        known = true;
        planningCone = Size.SMALL;
    }
}