package core;

import utility.*;

public class Pooh extends Person implements ThrowAble {
    static Pooh pooh;
    private boolean winner = false;
    private boolean known = true;
    private Size planningCone = Size.SMALL;

    private Pooh() {
        super("Персонаж");
        joinStory();
    }

    private Pooh(final String name) {
        super(name);
        joinStory();
    }

    public static Pooh getInstance() {
        if (pooh == null) pooh = new Pooh("Пух");
        return pooh;
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
        System.out.println("Персонаж '" + name + "' присоединился к истории.");
    }

    @Override
    public String toString() {
        return "Персонаж '" + name + "'";
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
        System.out.println("Персонаж '" + name + "' делает что-то.");
    }

    @Override
    public void waiting() {
        System.out.println("Персонаж '" + name + "' ждёт, какая из шишек выплывет  первой.");
    }

    @Override
    public void throwing(FloatAble coneFirst, FloatAble coneSecond) {
        if (coneFirst.getSize() == coneSecond.getSize()) known = false;
        if (known && planningCone == Size.BIG) winner = true;
        System.out.println("Персонаж '" + name + "' бросил " + "шишку " + coneFirst.getSize().toString() + " и шишку " + coneSecond.getSize().toString() + ".");
    }

    @Override
    public void knowing() {
        if (known) System.out.println("Персонаж '" + name + "' знает, какая шишка выплыла первой.");
        else System.out.println("Персонаж '" + name + "' не знает, какая шишка выплыла первой.");
    }

    @Override
    public void planning(FloatAble cone) {
        System.out.println("Персонаж '" + name + "' задумал, какая шишка выплывет первой.");
        planningCone = cone.getSize();
    }

    @Override
    public void winning() {
        if (winner) System.out.println("Персонаж '" + name + "' выиграл 2 раза.");
        else System.out.println("Персонаж '" + name + "' проиграл.");
    }

    @Override
    public void doingDefault() {
        winner = false;
        known = true;
        planningCone = Size.SMALL;
    }
}