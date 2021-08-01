package utils;

public interface VariableException {
    /**
     * If a value assigned to an argument breaks argument's restrictions.
     * Because it's part of the initial data, the program will stop.
     * */
    class InvalidInputValueError extends Error{
        public InvalidInputValueError(String message) {
            super(message);
        }
    }

    /**
     * When an object attempts to override another object with similar
     * properties, or when the program would usually create duplicates.
     * Because it's part of the initial data, the program will stop.
     * */
    class OverrideInputError extends Error{
        public OverrideInputError(String message) {
            super(message);
        }
    }
    /**
     * If a value assigned to an argument breaks argument's restrictions.
     * Because it's delivered from a command, the program will continue running.
     * */
    class InvalidCommandValueException extends Exception {
        public InvalidCommandValueException(String message) {
            super(message);
        }
    }

    /**
     * When an object attempts to override another object with similar
     * properties, or when the program would usually create duplicates.
     * Because it's delivered from a command, the program will continue running.
     */
    class OverrideCommandException extends Exception {
        public OverrideCommandException(String message) {
            super(message);
        }
    }

    /**
     * When the file misses an important structure, either "stock" or "clients".
     * This will stop the program if it's thrown.
     */
    class MissingStructureError extends Error {
        public MissingStructureError(String message) {
            super(message);
        }
    }

    /**
     * When the given command doesn't match any command due to syntax error.
     * This will not stop the program, and the user will be advised to type HELP
     * for the list of commands with their given short descriptions.
     */
    class InvalidCommand extends Exception {
        public InvalidCommand() {
            super("Invalid command, please type HELP for displaying the available " +
                    "commands");
        }
    }
}
