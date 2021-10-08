package Pokemons;

import Moves.*;
import ru.ifmo.se.pokemon.*;

public class Mudkip extends Pokemon {

    public Mudkip(java.lang.String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(50, 70, 50, 50, 50, 40);
        addMove(new Facade());
        addMove(new Rest());
    }
}
