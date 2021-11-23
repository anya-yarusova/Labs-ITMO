package Moves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {

    public Facade() {
        super(Type.NORMAL, 70, 100);
    }

    @Override
    protected String describe() {
        return "uses Facade";
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double damage) {
        if (pokemon.getCondition() == Status.BURN || pokemon.getCondition() == Status.PARALYZE || pokemon.getCondition() == Status.POISON) {
            super.applyOppDamage(pokemon, 2 * damage);
        } else {
            super.applyOppDamage(pokemon, damage);
        }
    }
}
