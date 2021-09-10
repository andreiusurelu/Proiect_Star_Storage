package datastorage;

public enum StorageType {

    DATA_STRUCTURE("DataStructure"),
    DATA_BASE("Database");

    public final String text;

    StorageType(String text) {
        this.text = text;
    }

    public static StorageType fromString(String text) {
        for (StorageType type : StorageType.values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }

    public static boolean notStorageType(String text) {
        for (StorageType type : StorageType.values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return false;
            }
        }
        return true;
    }
}
