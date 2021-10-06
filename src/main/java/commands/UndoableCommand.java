package commands;

import commandhandling.Visitor;

public interface UndoableCommand extends Command{
    @Override
    void execute();
    void undo();

    @Override
    default void accept(Visitor v) {
        v.visit(this);
    }
}
