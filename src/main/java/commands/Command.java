package commands;

import utils.VariableException;

import java.io.IOException;

public interface Command {
    void execute() throws IOException,
            VariableException.OverrideCommandException,
            VariableException.InvalidCommandValueException;

    void undo();
}
