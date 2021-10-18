package dataprocessing;

import commands.CommandType;

public class ConsolePrintStrategy implements PrintStrategy{

    @Override
    public void print(String message) {
        if (CommandType.notCommand(message)) {
            System.out.println(message);
        }
    }
    @Override
    public String getStrategyDescription() {
        return "This strategy will print to the console.";
    }

    @Override
    public String getDisplayMode() {
        return "CONSOLE";
    }

    @Override
    public void close() {

    }
}
