package dataprocessing;

public class PrintStrategyFactory {
    public static PrintStrategy createStrategy(String strategyName, String ...args) {
        try {
            PrintType type = PrintType.fromString(strategyName);
            if (type == null) {
                throw new IllegalArgumentException("Invalid strategy name");
            }
            return switch (type) {
                case CONSOLE -> new ConsolePrintStrategy();
                case FILE -> new OutputFilePrintStrategy(args[0]);
            };
        }

        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        return switch (strategyName) {
            case "CONSOLE" -> new ConsolePrintStrategy();
            case "FILE" -> new OutputFilePrintStrategy(args[0]);
            default -> null;
        };
    }
}
