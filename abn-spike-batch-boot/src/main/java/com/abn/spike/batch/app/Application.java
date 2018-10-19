package com.abn.spike.batch.app;


import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("com.abn")
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        //context.getBean(SpringExtensions.class);
        System.out.println("Starting up");

        Thread.sleep(50000);
        System.out.println("ShuttingDown");


    }
}