package com.hackathon.carteiravacinal.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hackathon.carteiravacinal.config.DatabaseConfig;
import com.hackathon.carteiravacinal.exceptions.ApiException;

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

}
