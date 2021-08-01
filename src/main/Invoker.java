package main;
import commands.Command;
import utils.VariableException;

import java.io.IOException;
import java.util.LinkedList;

public class Invoker {
    public LinkedList<Command> commandList;

    public Invoker() {
        commandList = new LinkedList<>();
    }

    public void execute(Command command) throws VariableException.InvalidCommandValueException,
            VariableException.OverrideCommandException, IOException {
        commandList.add(command);
        command.execute();
    }
}
