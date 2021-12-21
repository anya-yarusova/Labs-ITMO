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

    public class Feet {
        public void notseeing() {
            System.out.println( "Персонаж '" + name + "' не смотрел себе под ноги.");
        }
    }

    public class Paws {
        public void slipping(FloatAble cone) {
            System.out.println(cone.toString() + " выскользнула из лап "+ "персонажа '" + name + "'.");
        }
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
        System.out.println(this + " присоединился к истории.");
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
        System.out.println(this + " делает что-то.");
    }

    @Override
    public void saying(String message) {
        System.out.println(this + " сказал: '" + message + "'.");
    }

    @Override
    public void going() {
        System.out.println(this + " пошёл.");
    }

    @Override
    public void thinking() {
        System.out.println(this + " подумал.");
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
    public void seeing(SlideAble slide) {
        System.out.println(this + " смотрит на " + slide.toString() + ".");
        if (slide.toString().equals("Река")) {
            class Day {
                String name = "Денёк";

                void statusOfDay() {
                    System.out.println("Денёк такой славный.");
                }
            }

            Day day = new Day();
            day.statusOfDay();
        }
    }

    @Override
    public void waiting() {
        System.out.println(this + " ждёт, какая из шишек выплывет  первой.");
    }

    @Override
    public void throwing(FloatAble coneFirst, FloatAble coneSecond) throws PlanningConeException {
        if (coneFirst.getSize() == coneSecond.getSize()) known = false;
        if (planningCone != null) {
            if (known && planningCone == Size.BIG) winner = true;
            System.out.println(this + " бросил " + "шишку " + coneFirst.getSize().toString() + " и шишку " + coneSecond.getSize().toString() + ".");
        } else throw new PlanningConeException();
        }

    @Override
    public void knowing() {
        if (known) System.out.println(this + " знает, какая шишка выплыла первой.");
        else System.out.println(this + " не знает, какая шишка выплыла первой.");
    }

    @Override
    public void planning(FloatAble cone) {
        System.out.println(this + " задумал, какая шишка выплывет первой.");
        planningCone = cone.getSize();
    }

    @Override
    public void winning() {
        if (winner) System.out.println(this + " выиграл 2 раза.");
        else System.out.println(this + " проиграл.");
    }

    @Override
    public void doingDefault() {
        winner = false;
        known = true;
        planningCone = Size.SMALL;
    }

    @Override
    public void taking(FloatAble cone) {
        System.out.println(this + " набрал " + cone.toString() + ".");
    }

    public void stumbling() {
        System.out.println(this + " споткнулся.");
    }

    public void lying() {
        class Belly {
            String name = "Пузо";

            String placeOfLying() {
                return "на пузо";
            }
        }

        Belly belly = new Belly();
        System.out.println(this + " лёг "+ belly.placeOfLying() +".");
    }

    public void wantingToGo() {
        System.out.println(this + " хотел сходить за чем-то.");
    }

    public void wantingToGo(Thing thing) {
        System.out.println(this + " хотел сходить за " + thing.toString() + ".");
    }
}
