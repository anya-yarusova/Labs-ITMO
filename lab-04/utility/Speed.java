package utility;

public enum Speed {
    SLOWLY, SMOOTHLY;

    @Override
    public String toString() {
        switch (this) {
            case SLOWLY:
                return ("Медленно");
            case SMOOTHLY:
                return ("Плавно");
            default:
                throw new IllegalArgumentException();
        }
    }
}
