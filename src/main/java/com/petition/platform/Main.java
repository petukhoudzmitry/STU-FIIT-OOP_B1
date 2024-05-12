package com.petition.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

/**
 * Entry point of the petition platform application.
 */
@Controller
@SpringBootApplication
public class Main{
    /**
     * Default constructor for the Main class.
     */
    public Main() {}

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }
}