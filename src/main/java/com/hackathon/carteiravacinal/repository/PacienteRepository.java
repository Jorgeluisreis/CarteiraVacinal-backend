package com.hackathon.carteiravacinal.repository;

import com.hackathon.carteiravacinal.model.Paciente;
import com.hackathon.carteiravacinal.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacienteRepository {

    public Long adicionarPaciente(Paciente paciente) throws SQLException {
        String query = "INSERT INTO paciente (nome, cpf, sexo, data_nascimento) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getSexo().name());
            stmt.setDate(4, java.sql.Date.valueOf(paciente.getDataNascimento()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }

            throw new SQLException("Falha ao inserir o paciente.");
        }
    }
}