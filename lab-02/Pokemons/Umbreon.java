package Pokemons;

import ru.ifmo.se.pokemon.*;
import Moves.*;

public class Umbreon extends Eevee {

    public Umbreon(java.lang.String name, int level) {
        super(name, level);
        setType(Type.DARK);
        setStats(95, 65, 110, 60, 130, 65);
        addMove(new ShadowBall());
    }
}

