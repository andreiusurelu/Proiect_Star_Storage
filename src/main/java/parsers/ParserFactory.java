package parsers;

public class ParserFactory {
    public static Parser createParser(String fileType) {
        return switch (fileType) {
            case "JSON" -> new JSONParser();
            case "XML" -> null;
            default -> null;
        };
    }
}
