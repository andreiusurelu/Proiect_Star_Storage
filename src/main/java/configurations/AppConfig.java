package configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"main_components"})
public class AppConfig {

//    @Inject
//    private Environment env;
//    @PostConstruct
//    private void init() {
//        //System.out.println("Environment " + env.toString());
//    }
//    @Bean(name = "shop")
//    public Receiver createReceiver() {
//        return new Receiver();
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
