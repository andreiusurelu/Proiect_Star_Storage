package commands;

import main_components.Invoker;
import main_components.Receiver;

public class Undo implements SimpleCommand{
    private final Invoker invoker;
    private final Receiver receiver;
    public Undo(Receiver receiver, Invoker invoker) {
        this.invoker = invoker;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.write("UNDO");
        invoker.undo();
    }
}
