package com.hackathon.carteiravacinal.repository;

import com.hackathon.carteiravacinal.model.Paciente;
import com.hackathon.carteiravacinal.config.DatabaseConfig;
import com.hackathon.carteiravacinal.exceptions.ApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

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

    public boolean alterarPaciente(Long idPaciente, Paciente paciente) throws SQLException, ApiException {
        validarPaciente(paciente);

        String query = "UPDATE paciente SET nome = ?, cpf = ?, sexo = ?, data_nascimento = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getSexo().name());
            stmt.setDate(4, java.sql.Date.valueOf(paciente.getDataNascimento()));
            stmt.setLong(5, idPaciente);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        }
    }

    public Paciente buscarPacientePorId(Long id) throws ApiException {
        String query = "SELECT * FROM paciente WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(resultSet.getLong("id"));
                paciente.setNome(resultSet.getString("nome"));
                paciente.setCpf(resultSet.getString("cpf"));
                paciente.setSexo(Paciente.Sexo.valueOf(resultSet.getString("sexo")));
                paciente.setDataNascimento(resultSet.getDate("data_nascimento").toLocalDate());
                return paciente;
            } else {
                throw new ApiException("Paciente não encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para buscar o paciente: " + e.getMessage(), e);
        }
    }

    public boolean excluirPaciente(Long idPaciente) throws ApiException {
        String query = "DELETE FROM paciente WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, idPaciente);
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ApiException("Não é possível excluir o paciente pois há imunizações cadastradas para ele.");
        } catch (SQLException e) {
            throw new ApiException("Erro ao excluir o paciente.");
        }
    }

    public List<Paciente> consultarTodosPacientes() throws ApiException {
        String query = "SELECT * FROM paciente";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(resultSet.getLong("id"));
                paciente.setNome(resultSet.getString("nome"));
                paciente.setCpf(resultSet.getString("cpf"));
                paciente.setSexo(Paciente.Sexo.valueOf(resultSet.getString("sexo")));
                paciente.setDataNascimento(resultSet.getDate("data_nascimento").toLocalDate());
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para listar os pacientes: " + e.getMessage(), e);
        }

        return pacientes;
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