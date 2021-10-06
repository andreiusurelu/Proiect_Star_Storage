package main_components;

import configurations.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import utils.VariableException;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Client client = context.getBean("client", Client.class);

        System.out.println("Type HELP to show available commands");

        while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parameters = line.split(" ");
                //client.executeAction(line);
                switch (parameters[0]) {
                    case "UNDO" ->
                        client.undoAction();
                    case  "REDO" ->
                        client.redoAction();
                    case "HELP" ->
                        client.executeAction("HELP");
                    case "EXPORT" ->
                        client.executeAction("EXPORT", parameters[1]);
                    case "EXIT" -> {
                        client.executeAction("EXIT");
                        return;
                    }
                    case "PRINT" -> {
                        switch (parameters[1]) {
                            case "PRODUCTS" -> {
                                switch (parameters[2]) {
                                    case "ALL" ->
                                            client.executeAction("PRINT PRODUCTS ALL");

                                    case "CATEGORY" ->
                                            client.executeAction("PRINT PRODUCTS CATEGORY", parameters[3]);

                                    default ->
                                            client.executeAction("PRINT PRODUCTS", parameters[2]);

                                }
                            }
                            case "CATEGORIES" ->
                                    client.executeAction("PRINT CATEGORIES");

                            case "DISPLAY_MODE" ->
                                    client.executeAction("PRINT DISPLAY_MODE");

                            default ->
                                    logger.info(new VariableException.InvalidCommand());

                        }
                    }
                    case "ADD" -> {
                        if (parameters[1].equals("NEW")) {
                            switch (parameters[2]) {
                                case "CATEGORY" -> client.executeAction("ADD NEW CATEGORY", parameters[3]);
                                case "PRODUCT" ->
                                        client.executeAction("ADD NEW PRODUCT", parameters[3], parameters[4],
                                                parameters[5], parameters[6], parameters[7]);

                                default ->
                                        logger.info(new VariableException.InvalidCommand());

                            }
                        }
                    }
                    case "BUY" -> {
                        if (parameters[3].equals("FOR")) {
                            client.executeAction("BUY", parameters[1], parameters[2], parameters[4]);
                        }
                        else {
                            logger.info(new VariableException.InvalidCommand());
                        }
                    }
                    case "REPLENISH" ->
                            client.executeAction("REPLENISH", parameters[1], parameters[2]);

                    case "REMOVE" -> {
                        if (parameters[1].equals("PRODUCT")) {
                            client.executeAction("REMOVE PRODUCT", parameters[2]);
                        }
                        else {
                            logger.info(new VariableException.InvalidCommand());
                        }
                    }
                    case "SWITCH" -> {
                        if (!parameters[1].equals("DISPLAY_MODE")) {
                            logger.info(new VariableException.InvalidCommand());
                        }
                        String[] arguments = java.util.Arrays
                                .stream(parameters, 2, parameters.length).toArray(String[]::new);
                        client.executeAction("SWITCH DISPLAY_MODE", arguments);
                    }

                    default ->
                            logger.info(new VariableException.InvalidCommand());

                }

        }
    }
}
