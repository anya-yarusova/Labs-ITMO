import core.*;
import utility.*;

public class Lab_03 {

    public static void main(String[] args) {
        They friends = new They("Они");
        friends.doing();
        Pooh.getInstance().doing();
        Cone coneFirst = new Cone();
        Cone coneSecond = new Cone();
        Pooh.getInstance().planning(coneFirst);
        coneFirst.compare(coneSecond);
        Pooh.getInstance().throwing(coneFirst, coneSecond);
        Pooh.getInstance().waiting();
        coneFirst.surfaceOrdered(coneSecond);
        Pooh.getInstance().knowing();
        Pooh.getInstance().winning();
        Pooh.getInstance().doingDefault();
        System.out.println("В следующий раз:");
        Cone coneThird = new Cone(Size.BIG);
        Cone coneFourth = new Cone(Size.SMALL);
        Pooh.getInstance().planning(coneThird);
        coneThird.compare(coneFourth);
        Pooh.getInstance().throwing(coneThird, coneFourth);
        Pooh.getInstance().waiting();
        coneThird.surfaceOrdered(coneFourth);
        Pooh.getInstance().knowing();
        Pooh.getInstance().winning();
    }
}