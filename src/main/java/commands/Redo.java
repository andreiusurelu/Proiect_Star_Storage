package commands;

import main_components.Invoker;
import main_components.Receiver;

public class Redo implements SimpleCommand{
    private final Receiver receiver;
    private final Invoker invoker;
    public Redo(Receiver receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        receiver.write("REDO");
        invoker.redo();
    }
}
