package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"requestHandler", "BL", "DAL", "entities"})
public class App {


    public static void main(String[] args) {
        try {
            SpringApplication.run(App.class, args);
            System.out.println("Server is up!");
        } catch (Exception e) {
            System.out.println("Server couldn't start\n" + e.getMessage());
        }
    }


}
