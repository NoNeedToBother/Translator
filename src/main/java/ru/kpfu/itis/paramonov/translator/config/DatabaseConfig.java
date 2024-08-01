package ru.kpfu.itis.paramonov.translator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:database.properties")
public class DatabaseConfig {

    @Value("jdbc.driver") private String driver;

    @Value("jdbc.url") private String url;

    @Value("jdbc.user") private String user;

    @Value("jdbc.password") private String password;

    @Bean
    public Connection connection() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
