package Pokemons;

import Moves.*;
import ru.ifmo.se.pokemon.*;

public class Marshtomp extends Mudkip {

    public Marshtomp(java.lang.String name, int level) {
        super(name, level);
        addType(Type.GROUND);
        setStats(70, 85, 70, 60, 70, 50);
        addMove(new Psybeam());
    }
}

