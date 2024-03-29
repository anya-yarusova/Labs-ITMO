package Moves;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {

    public DoubleTeam() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected String describe() {
        return "uses Double Team";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.EVASION, 1);
    }
}
