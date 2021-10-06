package main_components;
import commandhandling.Visitor;
import commands.Command;
import commands.SimpleCommand;
import commands.UndoableCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.Stack;

@Component
public class Invoker {
    private static final Logger logger = LogManager.getLogger(Invoker.class);
    private Stack<UndoableCommand> commandList;
    private Stack<UndoableCommand> undoneCommandList;


    private class CommandHandler implements Visitor {
        @Override
        public void visit(SimpleCommand simpleCommand) {
            simpleCommand.execute();
        }

        @Override
        public void visit(UndoableCommand undoableCommand) {
            commandList.push(undoableCommand);
            undoableCommand.execute();
            undoneCommandList.clear();
        }
    }

    public Invoker() {
        commandList = new Stack<>();
        undoneCommandList = new Stack<>();
    }


    public void execute(Command command) {
        command.accept(new CommandHandler());
    }


    public void undo() {
        if (!commandList.empty()) {
            UndoableCommand lastCommand = commandList.pop();
            undoneCommandList.push(lastCommand);
            lastCommand.undo();
        }
        else {
            logger.error("No action available to undo");
        }
    }

    public void redo() {
        if (!undoneCommandList.empty())  {
            UndoableCommand undoneLastCommand = undoneCommandList.pop();
            commandList.add(undoneLastCommand);
            undoneLastCommand.execute();
        }
    }
}
