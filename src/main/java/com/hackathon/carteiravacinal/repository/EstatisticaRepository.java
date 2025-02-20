package com.hackathon.carteiravacinal.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import com.hackathon.carteiravacinal.config.DatabaseConfig;
import com.hackathon.carteiravacinal.exceptions.ApiException;
import com.hackathon.carteiravacinal.model.Paciente;

public class EstatisticaRepository {

    public int contarVacinasAplicadasPorPaciente(Long id) throws ApiException {
        String query = "SELECT COUNT(*) AS total FROM imunizacoes WHERE id_paciente = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

            return 0;
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para contar vacinas aplicadas: " + e.getMessage(),
                    e);
        }
    }

    public int qtdeVacinasProximasPorPaciente(Paciente paciente) throws ApiException {
        String query = """
                    SELECT COUNT(*) AS total FROM dose d
                    INNER JOIN vacina v ON d.id_vacina = v.id
                    WHERE d.idade_recomendada_aplicacao = ?
                    AND (v.limite_aplicacao IS NULL OR d.idade_recomendada_aplicacao <= v.limite_aplicacao)
                    AND d.id NOT IN (
                        SELECT i.id_dose FROM imunizacoes i WHERE i.id_paciente = ?
                    )
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDate dataNascimento = paciente.getDataNascimento();
            int idadeEmMeses = (int) Period.between(dataNascimento, LocalDate.now()).toTotalMonths();

            stmt.setInt(1, idadeEmMeses + 1);
            stmt.setLong(2, paciente.getId());

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para contar vacinas próximas: " + e.getMessage(),
                    e);
        }
    }

    public int qtdeVacinasAtrasadasPorPaciente(Paciente paciente) throws ApiException {
        String query = """
                    SELECT COUNT(*) AS total FROM dose d
                    INNER JOIN vacina v ON d.id_vacina = v.id
                    WHERE d.idade_recomendada_aplicacao < ?
                    AND (v.limite_aplicacao IS NULL OR d.idade_recomendada_aplicacao <= v.limite_aplicacao)
                    AND d.id NOT IN (
                        SELECT i.id_dose FROM imunizacoes i WHERE i.id_paciente = ?
                    )
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDate dataNascimento = paciente.getDataNascimento();
            int idadeEmMeses = (int) Period.between(dataNascimento, LocalDate.now()).toTotalMonths();

            stmt.setInt(1, idadeEmMeses);
            stmt.setLong(2, paciente.getId());

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para contar vacinas atrasadas: " + e.getMessage(),
                    e);
        }
    }
}
