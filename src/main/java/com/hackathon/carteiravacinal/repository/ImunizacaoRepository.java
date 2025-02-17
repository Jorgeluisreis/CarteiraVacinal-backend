package com.hackathon.carteiravacinal.repository;

import com.hackathon.carteiravacinal.model.Imunizacoes;
import com.hackathon.carteiravacinal.config.DatabaseConfig;
import com.hackathon.carteiravacinal.exceptions.ApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImunizacaoRepository {

    public Long adicionarImunizacao(Imunizacoes imunizacao) throws SQLException, ApiException {
        String query = "INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, imunizacao.getIdPaciente());
            stmt.setLong(2, imunizacao.getIdDose());
            stmt.setDate(3, java.sql.Date.valueOf(imunizacao.getDataAplicacao()));
            stmt.setString(4, imunizacao.getFabricante());
            stmt.setString(5, imunizacao.getLote());
            stmt.setString(6, imunizacao.getLocalAplicacao());
            stmt.setString(7, imunizacao.getProfissionalAplicador());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }

            throw new SQLException("Falha ao inserir imunização.");
        }
    }

    public Imunizacoes buscarImunizacaoPorId(Long id) throws ApiException {
        String query = "SELECT * FROM imunizacoes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Imunizacoes imunizacao = new Imunizacoes();

                // Conversão segura de long para int
                int imunizacaoId = Math.toIntExact(resultSet.getLong("id"));
                int doseId = Math.toIntExact(resultSet.getLong("id_dose"));

                imunizacao.setId(imunizacaoId);
                imunizacao.setIdPaciente(resultSet.getLong("id_paciente"));
                imunizacao.setIdDose(doseId);
                imunizacao.setDataAplicacao(resultSet.getDate("data_aplicacao").toLocalDate());
                imunizacao.setFabricante(resultSet.getString("fabricante"));
                imunizacao.setLote(resultSet.getString("lote"));
                imunizacao.setLocalAplicacao(resultSet.getString("local_aplicacao"));
                imunizacao.setProfissionalAplicador(resultSet.getString("profissional_aplicador"));

                return imunizacao;
            } else {
                throw new ApiException("Imunização não encontrada com o ID fornecido.");
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para buscar a imunização: " + e.getMessage(), e);
        } catch (ArithmeticException e) {
            throw new ApiException("Erro de conversão: ID da imunização ou ID da dose é muito grande para um inteiro.",
                    e);
        }
    }

    public boolean alterarImunizacao(Long id, Imunizacoes imunizacao) throws ApiException {
        String query = "UPDATE imunizacoes SET id_paciente = ?, id_dose = ?, data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, imunizacao.getIdPaciente());
            stmt.setLong(2, imunizacao.getIdDose());
            stmt.setDate(3, java.sql.Date.valueOf(imunizacao.getDataAplicacao()));
            stmt.setString(4, imunizacao.getFabricante());
            stmt.setString(5, imunizacao.getLote());
            stmt.setString(6, imunizacao.getLocalAplicacao());
            stmt.setString(7, imunizacao.getProfissionalAplicador());
            stmt.setLong(8, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new ApiException("Erro ao atualizar a imunização no banco de dados: " + e.getMessage());
        }
    }

    public boolean excluirImunizacao(Long idImunizacao) throws SQLException, ApiException {
        String query = "DELETE FROM imunizacoes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, idImunizacao);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        }
    }
}