import core.*;
import utility.*;

public class Lab_04 {

    public static void main(String[] args) throws PlanningConeException {
        Cone coneFirst = new Cone();
        Pooh.getInstance().new Feet().notseeing();
        Pooh.getInstance().stumbling();
        Pooh.getInstance().new Paws().slipping(coneFirst);
        coneFirst.falling();
        Pooh.getInstance().saying("Обидно!");
        Pooh.getInstance().seeing(coneFirst);
        Bridge.Side side = new Bridge.Side();
        coneFirst.sliding(side.whereToGo());
        Cone newCone = new Cone("Новая Шишка");
        Pooh.getInstance().wantingToGo(newCone);
        newCone.rhyming();
        Pooh.getInstance().thinking();
        SlideAble river = new SlideAble() {
            private Speed speed = Speed.SLOWLY;

            @Override
            public String toString() {
                return "Река";
            }

            @Override
            public Speed getSpeed() {
                return speed;
            }

            @Override
            public void setSpeed(Speed newSpeed) {
                speed = newSpeed;
            }

            @Override
            public void sliding() {
                if (speed != null) System.out.print(speed + " ");
                System.out.println(this + " скользила вдаль.");
            }

            @Override
            public void sliding(String place) {
                if (speed != null) System.out.print(speed + " ");
                System.out.println(this + " скользила " + place);
            }
        };
        Pooh.getInstance().lying();
        Pooh.getInstance().seeing(river);
        river.sliding();
        river.setSpeed(Speed.SMOOTHLY);
        river.sliding();
        Bridge bridge = new Bridge();
        coneFirst.coming(bridge);
        coneFirst.sliding();
        coneFirst.setSpeed(Speed.SMOOTHLY);
        coneFirst.sliding();
        Pooh.getInstance().saying("Как интересно!");
        Pooh.getInstance().saying("Я  уронил  ее  с  той стороны, а она выплыла с этой! Интересно, все шишки так делают?");
        Pooh.getInstance().going();
        They cones = new They("Они");
        Pooh.getInstance().taking(cones);
        cones.doing();
        Pooh.getInstance().doing();
        Cone coneSecond = new Cone();
        Cone coneThird = new Cone();
        Pooh.getInstance().planning(coneSecond);
        coneSecond.compare(coneThird);
        Pooh.getInstance().throwing(coneSecond, coneThird);
        Pooh.getInstance().waiting();
        coneSecond.surfaceOrdered(coneThird);
        Pooh.getInstance().knowing();
        Pooh.getInstance().winning();
        Pooh.getInstance().doingDefault();
        System.out.println("В следующий раз:");
        Cone coneFourth = new Cone(Size.BIG);
        Cone coneFifth = new Cone(Size.SMALL);
        Pooh.getInstance().planning(coneFourth);
        coneFourth.compare(coneFifth);
        Pooh.getInstance().throwing(coneFourth, coneFifth);
        Pooh.getInstance().waiting();
        coneFourth.surfaceOrdered(coneFifth);
        Pooh.getInstance().knowing();
        Pooh.getInstance().winning();
    }
}