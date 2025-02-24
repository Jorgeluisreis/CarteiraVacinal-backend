package com.hackathon.carteiravacinal.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    private static String getEnv(String key, String defaultValue) {
        String envValue = System.getenv(key);
        return (envValue != null && !envValue.isEmpty()) ? envValue : dotenv.get(key, defaultValue);
    }

    private static final String HOST = getEnv("DB_HOST", "localhost");
    private static final String PORT = getEnv("DB_PORT", "3306");
    private static final String DATABASE = getEnv("DB_NAME", "vacinacao");
    private static final String USER = getEnv("DB_USER", "root");
    private static final String PASSWORD = getEnv("DB_PASSWORD", "carteira");
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE +
            "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar o driver JDBC", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}