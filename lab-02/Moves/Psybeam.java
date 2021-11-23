package Moves;

import ru.ifmo.se.pokemon.*;

public class Psybeam extends SpecialMove {

    public Psybeam() {
        super(Type.PSYCHIC, 65, 100);
    }

    @Override
    protected String describe() {
        return "uses Psybeam";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() < 0.1) {
            Effect.confuse(pokemon);
        }
    }
}