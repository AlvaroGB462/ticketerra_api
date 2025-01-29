package com.proyecto.ticketerra_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TicketerraWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketerraWebApplication.class, args);
    }
}
