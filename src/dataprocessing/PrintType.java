package dataprocessing;

public enum PrintType {
    CONSOLE("CONSOLE"),
    FILE("FILE");

    public final String text;

    PrintType(String text) {
        this.text = text;
    }

    public static PrintType fromString(String text) {
        for (PrintType type : PrintType.values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
    public static boolean notStrategy(String text) {
        for (PrintType type : PrintType.values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return false;
            }
        }
        return true;
    }
}
