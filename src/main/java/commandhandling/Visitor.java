package commandhandling;

import commands.SimpleCommand;
import commands.UndoableCommand;

public interface Visitor {
    public void visit(SimpleCommand simpleCommand);
    public void visit(UndoableCommand undoableCommand);
}
