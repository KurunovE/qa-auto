package enumirate;

public enum LoadStrategy {
    NORMAL("normal"),
    EAGER("eager"),
    NONE("none");

    private final String value;

    LoadStrategy(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
