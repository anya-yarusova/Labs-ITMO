package Pokemons;

import Moves.*;
import ru.ifmo.se.pokemon.*;

public class Swampert extends Marshtomp {

    public Swampert(java.lang.String name, int level) {
        super(name, level);
        setStats(100, 110, 90, 85, 90, 60);
        addMove(new Extrasensory());
    }
}
