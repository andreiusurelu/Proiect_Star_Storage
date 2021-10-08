package main_components;

import configurations.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {

        try (Scanner scan = new Scanner(System.in)) {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            Client client = context.getBean("concreteClient", ConcreteClient.class);

            System.out.println("Type HELP to show available commands");

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                client.executeAction(line);
            }
        }
        catch (Exception e) {
            logger.error("An exception was thrown: ", e);
        }
    }
}
