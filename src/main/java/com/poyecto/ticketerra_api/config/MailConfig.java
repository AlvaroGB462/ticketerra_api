package com.poyecto.ticketerra_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("al.ga.ba.jjgg@gmail.com");
        mailSender.setPassword("pphc lqlh huhn yzdq");  // Asegúrate de usar una contraseña de aplicación, no tu contraseña normal

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Esta es la configuración recomendada para Gmail
        props.put("mail.debug", "true");

        return mailSender;
    }
}
