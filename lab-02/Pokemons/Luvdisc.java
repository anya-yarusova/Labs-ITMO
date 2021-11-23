package Pokemons;

import Moves.*;
import ru.ifmo.se.pokemon.*;

public class Luvdisc extends Pokemon {

    public Luvdisc(java.lang.String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(43, 30, 55, 40, 65, 97);
        addMove(new Amnesia());
        addMove(new AncientPower());
        addMove(new Slam());
        addMove(new ViceGrip());
    }
}
