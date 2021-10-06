package commands;

import commandhandling.Visitor;

public interface Command {
    void execute();
    void accept(Visitor v);
}
