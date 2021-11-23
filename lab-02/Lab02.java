import Pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Lab02 {

    public static void main(String[] args) {
        Battle b = new Battle();
        Eevee p1 = new Eevee("Frodo", 1);
        Mudkip p2 = new Mudkip("Aragorn", 1);
        Luvdisc p3 = new Luvdisc("Boromir", 1);
        Marshtomp p4 = new Marshtomp("Gandalf", 2);
        Swampert p5 = new Swampert("Bilbo", 3);
        Umbreon p6 = new Umbreon("Sauron", 2);
        b.addAlly(p1);
        b.addFoe(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addAlly(p5);
        b.addFoe(p6);
        b.go();
    }
}