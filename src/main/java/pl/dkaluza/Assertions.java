package pl.dkaluza;

public final class Assertions {
    private Assertions() {

    }

    public static void assertArgumentNotNull(Object object) throws IllegalArgumentException {
        if (object == null) {
            throw new IllegalArgumentException("Argument is null");
        }
    }
}
