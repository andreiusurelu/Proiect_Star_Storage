package main_components;

import commands.Command;

public interface Invoker {
    void execute(Command command);
    void undo();
    void redo();
}
