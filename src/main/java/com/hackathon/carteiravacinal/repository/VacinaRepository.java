package com.hackathon.carteiravacinal.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hackathon.carteiravacinal.config.DatabaseConfig;
import com.hackathon.carteiravacinal.exceptions.ApiException;
import com.hackathon.carteiravacinal.model.Vacina;

public class VacinaRepository {

    public List<Vacina> consultarTodasVacinas() throws ApiException {
        String query = "SELECT " +
                "  d.id, " +
                "  v.vacina, " +
                "  d.dose, " +
                "  d.idade_recomendada_aplicacao, " +
                "  v.limite_aplicacao, " +
                "  v.publico_alvo " +
                "FROM vacina v " +
                "INNER JOIN dose d ON v.id = d.id_vacina";

        List<Vacina> vacinas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Vacina vacina = new Vacina();
                vacina.setId(resultSet.getLong("id"));
                vacina.setVacina(resultSet.getString("vacina"));
                vacina.setDose(resultSet.getString("dose"));
                vacina.setidadeRecomendadaMeses(resultSet.getInt("idade_recomendada_aplicacao"));
                vacina.setLimiteAplicacao(resultSet.getInt("limite_aplicacao"));

                String publicoAlvoStr = resultSet.getString("publico_alvo");
                vacina.setPublicoAlvo(Vacina.PublicoAlvo.valueOf(publicoAlvoStr));

                vacinas.add(vacina);
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para listar as vacinas: " + e.getMessage(), e);
        }

        return vacinas;
    }

    public List<Vacina> consultarTodasVacinasPorFaixaEtaria(Vacina.PublicoAlvo faixaEtaria) throws ApiException {
        String query = "SELECT " +
                "  d.id, " +
                "  v.vacina, " +
                "  d.dose, " +
                "  d.idade_recomendada_aplicacao, " +
                "  v.limite_aplicacao, " +
                "  v.publico_alvo " +
                "FROM vacina v " +
                "INNER JOIN dose d ON v.id = d.id_vacina " +
                "WHERE v.publico_alvo = ?";

        List<Vacina> vacinas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, faixaEtaria.name());
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Vacina vacina = new Vacina();
                vacina.setId(resultSet.getLong("id"));
                vacina.setVacina(resultSet.getString("vacina"));
                vacina.setDose(resultSet.getString("dose"));
                vacina.setidadeRecomendadaMeses(resultSet.getInt("idade_recomendada_aplicacao"));
                vacina.setLimiteAplicacao(resultSet.getInt("limite_aplicacao"));

                String publicoAlvoStr = resultSet.getString("publico_alvo");
                vacina.setPublicoAlvo(Vacina.PublicoAlvo.valueOf(publicoAlvoStr));

                vacinas.add(vacina);
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para listar as vacinas: " + e.getMessage(), e);
        }

        return vacinas;
    }
}
