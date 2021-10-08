package Moves;

import ru.ifmo.se.pokemon.*;

public class Extrasensory extends SpecialMove {

    public Extrasensory() {
        super(Type.PSYCHIC, 80, 100);
    }

    @Override
    protected String describe() {
        return "uses Extrasensory";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() < 0.1) {
            Effect.flinch(pokemon);
        }
    }
}