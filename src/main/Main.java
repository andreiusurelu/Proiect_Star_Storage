package main;

import utils.VariableException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Client client = new Client("D:\\324cdb-andreiusurelu-master\\Proiect_Star_Storage\\output.json");
        Scanner scan = new Scanner(System.in);

        System.out.println("Type HELP to show available commands");

        while (scan.hasNextLine()) {
            try {
                String line = scan.nextLine();
                String[] parameters = line.split(" ");
                switch (parameters[0]) {
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
                                    throw new VariableException.InvalidCommand();

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
                                        throw new VariableException.InvalidCommand();

                            }
                        }
                    }
                    case "BUY" -> {
                        if (parameters[3].equals("FOR")) {
                            client.executeAction("BUY", parameters[1], parameters[2], parameters[4]);
                        }
                        else {
                            throw new VariableException.InvalidCommand();
                        }
                    }
                    case "REPLENISH" ->
                            client.executeAction("REPLENISH", parameters[1], parameters[2]);

                    case "REMOVE" -> {
                        if (parameters[1].equals("PRODUCT")) {
                            client.executeAction("REMOVE PRODUCT", parameters[2]);
                        }
                        else {
                            throw new VariableException.InvalidCommand();
                        }
                    }
                    case "SWITCH" -> {
                        if (!parameters[1].equals("DISPLAY_MODE")) {
                            throw new VariableException.InvalidCommand();
                        }
                        if (parameters[2].equals("CONSOLE")) {
                            client.executeAction("SWITCH DISPLAY_MODE", parameters[2]);
                        }
                        else if (parameters[2].equals("FILE")) {
                            client.executeAction("SWITCH DISPLAY_MODE", parameters[3]);
                        }
                        else {
                            throw new VariableException.InvalidCommand();
                        }
                    }

                    default ->
                            throw new VariableException.InvalidCommand();

                }
            }
            catch (VariableException.InvalidCommand e) {
                //de implementat cu logger gestionarea erorilor/exceptiilor
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
