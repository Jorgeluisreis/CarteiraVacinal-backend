package com.hackathon.carteiravacinal.repository;

import com.hackathon.carteiravacinal.model.Imunizacoes;
import com.hackathon.carteiravacinal.config.DatabaseConfig;
import com.hackathon.carteiravacinal.exceptions.ApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public boolean verificarImunizacaoExistente(Long idPaciente, int idDose) throws SQLException {
        String query = "SELECT COUNT(*) FROM imunizacoes WHERE id_paciente = ? AND id_dose = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, idPaciente);
            stmt.setLong(2, idDose);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
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

    public boolean excluirTodasImunizacoesPaciente(Long idPaciente) throws SQLException, ApiException {
        String query = "DELETE FROM imunizacoes WHERE id_paciente = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, idPaciente);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        }
    }

    public List<Imunizacoes> consultarTodasImunizacoes() throws ApiException {
        String query = """
                SELECT i.id AS id_imunizacao, p.nome AS paciente, v.vacina AS nome_vacina, d.dose AS dose,
                       i.data_aplicacao, i.fabricante, i.lote, i.local_aplicacao, i.profissional_aplicador
                FROM imunizacoes i
                INNER JOIN paciente p ON i.id_paciente = p.id
                INNER JOIN dose d ON i.id_dose = d.id
                INNER JOIN vacina v ON d.id_vacina = v.id;
                """;

        List<Imunizacoes> imunizacoes = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Imunizacoes imunizacao = new Imunizacoes();
                imunizacao.setId(resultSet.getInt("id_imunizacao"));
                imunizacao.setNome(resultSet.getString("paciente"));
                imunizacao.setVacina(resultSet.getString("nome_vacina"));
                imunizacao.setDose(resultSet.getString("dose"));
                imunizacao.setDataAplicacao(resultSet.getDate("data_aplicacao").toLocalDate());
                imunizacao.setFabricante(resultSet.getString("fabricante"));
                imunizacao.setLote(resultSet.getString("lote"));
                imunizacao.setLocalAplicacao(resultSet.getString("local_aplicacao"));
                imunizacao.setProfissionalAplicador(resultSet.getString("profissional_aplicador"));
                imunizacoes.add(imunizacao);
            }
        } catch (SQLException e) {
            throw new ApiException(
                    "Erro ao acessar o banco de dados para listar todas as imunizações: " + e.getMessage(), e);
        }
        return imunizacoes;
    }

    public List<Imunizacoes> consultarImunizacaoPorIdImunizacao(Long id) throws ApiException {
        List<Imunizacoes> imunizacoes = new ArrayList<>();

        String query = """
                SELECT i.id AS id_imunizacao, p.nome AS paciente, v.vacina AS nome_vacina, d.dose AS dose,
                       i.data_aplicacao, i.fabricante, i.lote, i.local_aplicacao, i.profissional_aplicador
                FROM imunizacoes i
                INNER JOIN paciente p ON i.id_paciente = p.id
                INNER JOIN dose d ON i.id_dose = d.id
                INNER JOIN vacina v ON d.id_vacina = v.id
                WHERE i.id = ?;
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Imunizacoes imunizacao = new Imunizacoes();

                imunizacao.setId(resultSet.getInt("id_imunizacao"));
                imunizacao.setNome(resultSet.getString("paciente"));
                imunizacao.setVacina(resultSet.getString("nome_vacina"));
                imunizacao.setDose(resultSet.getString("dose"));
                imunizacao.setDataAplicacao(resultSet.getDate("data_aplicacao").toLocalDate());
                imunizacao.setFabricante(resultSet.getString("fabricante"));
                imunizacao.setLote(resultSet.getString("lote"));
                imunizacao.setLocalAplicacao(resultSet.getString("local_aplicacao"));
                imunizacao.setProfissionalAplicador(resultSet.getString("profissional_aplicador"));
                imunizacoes.add(imunizacao);
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para buscar a imunização: " + e.getMessage(), e);
        } catch (ArithmeticException e) {
            throw new ApiException("Erro de conversão: ID da imunização ou ID da dose é muito grande para um inteiro.",
                    e);
        }
        return imunizacoes;
    }

    public List<Imunizacoes> consultarImunizacaoPorIdPaciente(Long id) throws ApiException {
        List<Imunizacoes> imunizacoes = new ArrayList<>();

        String query = """
                SELECT i.id AS id_imunizacao, p.nome AS paciente, v.vacina AS nome_vacina, d.dose AS dose,
                       i.data_aplicacao, i.fabricante, i.lote, i.local_aplicacao, i.profissional_aplicador
                FROM imunizacoes i
                INNER JOIN paciente p ON i.id_paciente = p.id
                INNER JOIN dose d ON i.id_dose = d.id
                INNER JOIN vacina v ON d.id_vacina = v.id
                WHERE p.id = ?;
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Imunizacoes imunizacao = new Imunizacoes();
                imunizacao.setId(resultSet.getInt("id_imunizacao"));
                imunizacao.setNome(resultSet.getString("paciente"));
                imunizacao.setVacina(resultSet.getString("nome_vacina"));
                imunizacao.setDose(resultSet.getString("dose"));
                imunizacao.setDataAplicacao(resultSet.getDate("data_aplicacao").toLocalDate());
                imunizacao.setFabricante(resultSet.getString("fabricante"));
                imunizacao.setLote(resultSet.getString("lote"));
                imunizacao.setLocalAplicacao(resultSet.getString("local_aplicacao"));
                imunizacao.setProfissionalAplicador(resultSet.getString("profissional_aplicador"));
                imunizacoes.add(imunizacao);
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para buscar a imunização: " + e.getMessage(), e);
        } catch (ArithmeticException e) {
            throw new ApiException("Erro de conversão: ID da do Paciente ou ID da dose é muito grande para um inteiro.",
                    e);
        }
        return imunizacoes;
    }

    public List<Imunizacoes> consultarImunizacaoPorIdeIntervaloAplicacao(Long id, LocalDate dtIni, LocalDate dtFim)
            throws ApiException {
        List<Imunizacoes> imunizacoes = new ArrayList<>();

        String query = """
                SELECT i.id AS id_imunizacao, p.nome AS paciente, v.vacina AS nome_vacina, d.dose AS dose,
                       i.data_aplicacao, i.fabricante, i.lote, i.local_aplicacao, i.profissional_aplicador
                FROM imunizacoes i
                INNER JOIN paciente p ON i.id_paciente = p.id
                INNER JOIN dose d ON i.id_dose = d.id
                INNER JOIN vacina v ON d.id_vacina = v.id
                WHERE p.id = ?
                AND i.data_aplicacao BETWEEN ? AND ?;
                """;
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            stmt.setLong(1, id);
            stmt.setString(2, dtIni.format(dbFormatter));
            stmt.setString(3, dtFim.format(dbFormatter));

            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Imunizacoes imunizacao = new Imunizacoes();
                imunizacao.setId(resultSet.getInt("id_imunizacao"));
                imunizacao.setNome(resultSet.getString("paciente"));
                imunizacao.setVacina(resultSet.getString("nome_vacina"));
                imunizacao.setDose(resultSet.getString("dose"));
                imunizacao.setDataAplicacao(resultSet.getDate("data_aplicacao").toLocalDate());
                imunizacao.setFabricante(resultSet.getString("fabricante"));
                imunizacao.setLote(resultSet.getString("lote"));
                imunizacao.setLocalAplicacao(resultSet.getString("local_aplicacao"));
                imunizacao.setProfissionalAplicador(resultSet.getString("profissional_aplicador"));
                imunizacoes.add(imunizacao);
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao acessar o banco de dados para o período de imunização: " + e.getMessage(),
                    e);
        } catch (ArithmeticException e) {
            throw new ApiException("Erro de conversão: ID da do Paciente ou ID da dose é muito grande para um inteiro.",
                    e);
        }
        return imunizacoes;
    }
}