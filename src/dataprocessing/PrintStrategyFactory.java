package dataprocessing;

public class PrintStrategyFactory {
    public static PrintStrategy createStrategy(String strategyName, String ...args) {
        return switch (strategyName) {
            case "CONSOLE" -> new ConsolePrintStrategy();
            case "FILE" -> new OutputFilePrintStrategy(args[0]);
            default -> null;
        };
    }
}
