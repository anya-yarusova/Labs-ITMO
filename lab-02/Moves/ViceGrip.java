package Moves;

import ru.ifmo.se.pokemon.*;

public class ViceGrip extends PhysicalMove {

    public ViceGrip() {
        super(Type.NORMAL, 55, 100);
    }

    @Override
    protected String describe() {
        return "uses Vice Grip";
    }
}