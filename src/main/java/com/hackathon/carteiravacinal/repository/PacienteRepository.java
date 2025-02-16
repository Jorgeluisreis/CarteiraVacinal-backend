package com.hackathon.carteiravacinal.repository;

import com.hackathon.carteiravacinal.model.Paciente;
import com.hackathon.carteiravacinal.config.DatabaseConfig;
import com.hackathon.carteiravacinal.exceptions.ApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacienteRepository {

    public Long adicionarPaciente(Paciente paciente) throws SQLException, ApiException {
        validarPaciente(paciente);

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

    private void validarPaciente(Paciente paciente) throws ApiException {
        if (paciente.getNome() == null || paciente.getNome().isEmpty() || paciente.getNome().length() > 60) {
            throw new ApiException("Nome inválido. O nome não pode ser vazio e deve ter no máximo 60 caracteres.");
        }

        if (paciente.getCpf() != null && (paciente.getCpf().length() != 11 || !paciente.getCpf().matches("\\d{11}"))) {
            throw new ApiException("CPF inválido. O CPF deve ter exatamente 11 dígitos numéricos.");
        }

        if (paciente.getSexo() == null) {
            throw new ApiException("Sexo inválido. Escolha entre 'M' ou 'F'.");
        }

        if (paciente.getDataNascimento() == null) {
            throw new ApiException("Data de nascimento inválida.");
        }
    }
}