package com.petition.platform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class Main{
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }
}