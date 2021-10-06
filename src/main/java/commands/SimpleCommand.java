package commands;

import commandhandling.Visitor;

public interface SimpleCommand extends Command{
    @Override
    void execute();

    @Override
    default void accept(Visitor v) {
        v.visit(this);
    }
}
