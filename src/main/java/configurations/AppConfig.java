package configurations;

import main.Client;
import main.Invoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import shop.Shop;

@Configuration
@ComponentScan(basePackages = {"main", "shop"})

public class AppConfig {
//    @Bean(name = "shop")
//    public Shop createShop() {
//        return new Shop();
//    }
//
//    @Bean(name = "invoker")
//    public Invoker createInvoker() {
//        return new Invoker();
//    }
//
//    @Bean(name = "client")
//    public Client createClient() {
//        return new Client(createShop(), createInvoker());
//    }
}
