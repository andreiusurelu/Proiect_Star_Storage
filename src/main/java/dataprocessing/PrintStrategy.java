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
    //de facut o alta interfata care sa extinda PrintStrategy si sa aiba close.
    void close();
}
