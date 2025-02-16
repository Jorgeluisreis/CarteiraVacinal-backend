package com.hackathon.carteiravacinal;

import java.sql.Connection;
import java.sql.SQLException;

import com.hackathon.carteiravacinal.config.DatabaseConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando CarteiraVacinal Backend...");

        try (Connection conn = DatabaseConfig.getConnection()) {
            if (conn != null) {
                System.out.println("Conectado ao banco de dados com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
