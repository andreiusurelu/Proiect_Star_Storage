package dataprocessing;

public interface PrintStrategy {
    /**
     * Prints to a specified output, according to the strategy.
     */
    void print(String message);
    /**
     * Get a concise description of the used strategy, used for info purposes.
     */
    String getStrategyDescription();
    /**
     * Get the current display mode of the used strategy.
     * @return display mode
     */
    String getDisplayMode();
    /**
     * Closes any connection or file.
     */
    void close();
}
