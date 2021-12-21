package core;

import utility.*;

public class Cone extends Thing implements FloatAble, SlideAble {
    private Size type = Size.SMALL;
    private Speed speed = Speed.SLOWLY;

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
        System.out.println(this + " присоединилась к истории.");
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
        System.out.println(this + " всплыла.");
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

    @Override
    public void doing() {
        System.out.println(this + " так делала.");
    }

    @Override
    public void coming() {
        System.out.println(this + " появилась.");
    }

    @Override
    public void coming(Thing place) {
        System.out.println(this + " появилась из-под " + place.toString() + ".");
    }

    @Override
    public void sliding() {
        if (speed != null) {
            System.out.print(speed + " ");
            System.out.println(this + " скользила вдаль.");
        } else throw new SpeedException();
    }

    @Override
    public void sliding(String place) {
        if (speed != null) {
            System.out.print(speed + " ");
            System.out.println(this + " скользила " + place);
        } else throw new SpeedException();
    }

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(Speed newSpeed) {
        speed = newSpeed;
    }

    public void falling() {
        class Water {
            String name = "Вода";

            String placeOfFalling() {
                return "в воду";
            }
        }

        Water water = new Water();
        System.out.println(this + " упала " + water.placeOfFalling() + ".");
    }

    public void rhyming() {
        System.out.println(this + " рифмуется.");
    }

}