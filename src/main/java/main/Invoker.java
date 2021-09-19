package main;
import commands.Command;
import org.springframework.stereotype.Component;
import utils.VariableException;

import java.io.IOException;
import java.util.Stack;

@Component
public class Invoker {
    private Stack<Command> commandList;
    private Stack<Command> undoneCommandList;


    public Invoker() {
        commandList = new Stack<>();
        undoneCommandList = new Stack<>();
    }

    public void execute(Command command) throws VariableException.InvalidCommandValueException,
            VariableException.OverrideCommandException, IOException {
        commandList.push(command);
        command.execute();
        undoneCommandList.clear();
    }

    public void undo() {
        if (!commandList.empty()) {
            Command lastCommand = commandList.pop();
            undoneCommandList.push(lastCommand);
            lastCommand.undo();
        }
    }

    public void redo() throws VariableException.OverrideCommandException,
            VariableException.InvalidCommandValueException, IOException{
        if (!undoneCommandList.empty())  {
            Command undoneLastCommand = undoneCommandList.pop();
            commandList.add(undoneLastCommand);
            undoneLastCommand.execute();
        }
    }
}
