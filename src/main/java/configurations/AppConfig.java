package configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"main_components", "shop"})
@PropertySource("classpath:config.properties")
public class AppConfig {

//    @Inject
//    private Environment env;
//    @PostConstruct
//    private void init() {
//        //System.out.println("Environment " + env.toString());
//    }
//    @Bean(name = "shop")
//    public Shop createShop() {
//        return new Shop(env.getProperty("driverClass"), env.getProperty("url"), env.getProperty("username"),
//                env.getProperty("password"));
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
