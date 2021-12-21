package utility;

public enum Size {
    SMALL, BIG;

    @Override
    public String toString() {
        switch (this) {
            case SMALL:
                return ("маленькая");
            case BIG:
                return ("большая");
            default:
                throw new IllegalArgumentException();
        }
    }
}