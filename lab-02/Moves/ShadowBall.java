package Moves;

import ru.ifmo.se.pokemon.*;

public class ShadowBall extends SpecialMove {

    public ShadowBall() {
        super(Type.GHOST, 90, 100);
    }

    @Override
    protected String describe() {
        return "uses Shadow Balll";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() < 0.2) {
            pokemon.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }
}