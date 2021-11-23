package Moves;

import ru.ifmo.se.pokemon.*;

public class Amnesia extends StatusMove {

    public Amnesia() {
        super(Type.PSYCHIC, 0, 0);
    }

    @Override
    protected String describe() {
        return "uses Amnesia";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_DEFENSE, 2);
    }
}
