package com.hackathon.carteiravacinal.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hackathon.carteiravacinal.model.*;
import com.hackathon.carteiravacinal.service.ImunizacaoService;
import com.hackathon.carteiravacinal.service.PacienteService;
import com.hackathon.carteiravacinal.util.GsonUtil;
import com.hackathon.carteiravacinal.exceptions.ApiException;
import spark.Request;
import spark.Response;
import spark.Route;

public class ImunizacaoApi {

    private ImunizacaoService imunizacaoService;
    private PacienteService pacienteService;
    private Gson gson;

    public ImunizacaoApi(ImunizacaoService imunizacaoService, PacienteService pacienteService) {
        this.imunizacaoService = imunizacaoService;
        this.pacienteService = pacienteService;
        this.gson = GsonUtil.getGson();
    }

    public Route adicionarImunizacao = (Request req, Response res) -> {
        try {
            Imunizacoes imunizacao = gson.fromJson(req.body(), Imunizacoes.class);

            if (imunizacao.getIdPaciente() == null) {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Erro: O ID do paciente é obrigatório.");
            }
            if (imunizacao.getDataAplicacao() == null) {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Erro: A data de aplicação é obrigatória.");
            }
            if (imunizacao.getIdDose() <= 0) {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Erro: O ID da dose é obrigatório e deve ser maior que zero.");
            }

            Long codigoImunizacao = imunizacaoService.adicionarImunizacao(imunizacao);

            res.status(201);
            res.type("application/json");
            return gson.toJson("Imunização inserida com sucesso! Código: " + codigoImunizacao);
        } catch (ApiException e) {
            if (e.getMessage().equals("Paciente não encontrado com o ID fornecido.")) {
                res.status(404);
            } else if (e.getMessage().contains("já possui esta imunização cadastrada")) {
                res.status(409);
            } else {
                res.status(400);
            }
            res.type("application/json");
            return gson.toJson(e.getMessage());
        } catch (JsonSyntaxException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro: Formato de dados inválido.");
        } catch (Exception e) {
            res.status(500);
            res.type("application/json");
            return gson.toJson("Erro inesperado: " + e.getMessage());
        }
    };

    public Route alterarImunizacao = (Request req, Response res) -> {
        try {
            Long idImunizacaoRequisitada = Long.parseLong(req.params(":id"));
            Imunizacoes imunizacaoRequisitada = imunizacaoService.buscarImunizacaoPorId(idImunizacaoRequisitada);

            if (imunizacaoRequisitada == null) {
                res.status(404);
                res.type("application/json");
                return gson.toJson("Erro: Imunização não encontrada.");
            }

            Imunizacoes imunizacao = gson.fromJson(req.body(), Imunizacoes.class);

            if (isImunizacaoIgual(imunizacaoRequisitada, imunizacao)) {
                res.status(400);
                res.type("application/json");
                return gson.toJson(
                        "Erro: Não há dados a serem alterados, os dados enviados são iguais aos existentes no banco de dados.");
            }

            Imunizacoes imunizacaoAlterada = imunizacaoRequisitada;
            boolean dadosAlterados = false;

            if (!imunizacao.getDataAplicacao().equals(imunizacaoRequisitada.getDataAplicacao())) {
                imunizacaoAlterada.setDataAplicacao(imunizacao.getDataAplicacao());
                dadosAlterados = true;
            }
            if (!imunizacao.getFabricante().equals(imunizacaoRequisitada.getFabricante())) {
                imunizacaoAlterada.setFabricante(imunizacao.getFabricante());
                dadosAlterados = true;
            }
            if (!imunizacao.getLote().equals(imunizacaoRequisitada.getLote())) {
                imunizacaoAlterada.setLote(imunizacao.getLote());
                dadosAlterados = true;
            }
            if (!imunizacao.getLocalAplicacao().equals(imunizacaoRequisitada.getLocalAplicacao())) {
                imunizacaoAlterada.setLocalAplicacao(imunizacao.getLocalAplicacao());
                dadosAlterados = true;
            }
            if (!imunizacao.getProfissionalAplicador().equals(imunizacaoRequisitada.getProfissionalAplicador())) {
                imunizacaoAlterada.setProfissionalAplicador(imunizacao.getProfissionalAplicador());
                dadosAlterados = true;
            }

            if (dadosAlterados) {
                boolean atualizado = imunizacaoService.alterarImunizacao(idImunizacaoRequisitada, imunizacaoAlterada);
                if (atualizado) {
                    res.status(200);
                    res.type("application/json");
                    return gson.toJson("Imunização atualizada com sucesso!");
                } else {
                    res.status(500);
                    res.type("application/json");
                    return gson.toJson("Erro ao atualizar imunização.");
                }
            } else {
                res.status(400);
                res.type("application/json");
                return gson.toJson(
                        "Erro: Não há dados a serem alterados, os dados enviados são iguais aos existentes no banco de dados.");
            }
        } catch (ApiException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro ao alterar imunização: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro no formato de dados: " + e.getMessage());
        } catch (Exception e) {
            res.status(500);
            res.type("application/json");
            return gson.toJson("Erro inesperado: " + e.getMessage());
        }
    };

    public Route excluirImunizacao = (Request req, Response res) -> {
        try {
            Long idImunizacaoRequisitada = Long.parseLong(req.params(":id"));

            imunizacaoService.buscarImunizacaoPorId(idImunizacaoRequisitada);

            boolean excluido = imunizacaoService.excluirImunizacao(idImunizacaoRequisitada);

            if (excluido) {
                res.status(200);
                res.type("application/json");
                return gson.toJson("Imunização excluída com sucesso!");
            } else {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Erro ao excluir Imunização.");
            }
        } catch (ApiException e) {
            if (e.getMessage().equals("Imunização não encontrada com o ID fornecido.")) {
                res.status(404);
                res.type("application/json");
                return gson.toJson(e.getMessage());
            }
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro ao excluir Imunização: " + e.getMessage());
        } catch (NumberFormatException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro no formato do ID.");
        }
    };

    public Route excluirTodasImunizacoesPaciente = (Request req, Response res) -> {
        try {
            Long idPacienteRequisitado = Long.parseLong(req.params(":id"));

            pacienteService.buscarPacientePorId(idPacienteRequisitado);

            boolean excluido = imunizacaoService.excluirTodasImunizacoesPaciente(idPacienteRequisitado);

            if (excluido) {
                res.status(200);
                res.type("application/json");
                return gson.toJson("Todas as imunizações do paciente foram excluídas com sucesso!");
            } else {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Erro ao excluir as Imunizações.");
            }
        } catch (ApiException e) {
            if (e.getMessage().equals("Imunização não encontrada com o ID fornecido.")) {
                res.status(404);
                res.type("application/json");
                return gson.toJson(e.getMessage());
            }
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro ao excluir as Imunizações: " + e.getMessage());
        } catch (NumberFormatException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro no formato do ID.");
        }
    };

    public Route consultarTodasImunizacoes = (Request req, Response res) -> {
        try {
            List<Imunizacoes> imunizacoes = imunizacaoService.consultarTodasImunizacoes();
            res.status(200);
            res.type("application/json");
            return gson.toJson(imunizacoes);
        } catch (ApiException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro ao listar as imunizações: " + e.getMessage());
        }
    };

    public Route consultarImunizacaoPorIdImunizacao = (Request req, Response res) -> {
        try {
            Long idImunizacao = Long.parseLong(req.params(":id"));
            List<Imunizacoes> imunizacao = imunizacaoService.consultarImunizacaoPorIdImunizacao(idImunizacao);

            if (imunizacao.isEmpty()) {
                res.status(404);
                res.type("application/json");
                return gson.toJson("Erro: Imunização não encontrada.");
            }

            res.status(200);
            res.type("application/json");
            return gson.toJson(imunizacao);
        } catch (NumberFormatException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("ID inválido. O ID deve ser um número.");
        } catch (ApiException e) {
            res.status(500);
            res.type("application/json");
            return gson.toJson("Erro ao consultar a imunização: " + e.getMessage());
        }
    };

    public Route consultarImunizacaoPorIdPaciente = (Request req, Response res) -> {
        try {
            Long idPaciente = Long.parseLong(req.params(":id"));

            pacienteService.buscarPacientePorId(idPaciente);

            List<Imunizacoes> imunizacao = imunizacaoService.consultarImunizacaoPorIdPaciente(idPaciente);

            if (imunizacao == null) {
                res.status(404);
                res.type("application/json");
                return gson.toJson("Imunização por paciente não encontrada.");
            }

            res.status(200);
            res.type("application/json");
            return gson.toJson(imunizacao);
        } catch (NumberFormatException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("ID inválido. O ID deve ser um número.");
        } catch (ApiException e) {
            res.status(500);
            res.type("application/json");
            return gson.toJson("Erro ao consultar a imunização por paciente: " + e.getMessage());
        }
    };

    public Route consultarImunizacaoPorIdeIntervaloAplicacao = (Request req, Response res) -> {
        try {
            Long idPaciente = Long.parseLong(req.params(":id"));
            String dtIniStr = req.params(":dt_ini");
            String dtFimStr = req.params(":dt_fim");

            pacienteService.buscarPacientePorId(idPaciente);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate dtIni = LocalDate.parse(dtIniStr, formatter);
            LocalDate dtFim = LocalDate.parse(dtFimStr, formatter);

            List<Imunizacoes> imunizacao = imunizacaoService.consultarImunizacaoPorIdeIntervaloAplicacao(idPaciente,
                    dtIni, dtFim);

            if (imunizacao == null) {
                res.status(404);
                res.type("application/json");
                return gson.toJson("Período de Imunização por paciente não encontrado.");
            }

            res.status(200);
            res.type("application/json");
            return gson.toJson(imunizacao);
        } catch (NumberFormatException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("ID inválido. O ID deve ser um número.");
        } catch (ApiException e) {
            res.status(500);
            res.type("application/json");
            return gson.toJson("Erro ao consultar o período de imunização por paciente: " + e.getMessage());
        }
    };

    private boolean isImunizacaoIgual(Imunizacoes imunizacaoBanco, Imunizacoes imunizacaoBody) {
        return imunizacaoBanco.getDataAplicacao().equals(imunizacaoBody.getDataAplicacao()) &&
                imunizacaoBanco.getFabricante().equals(imunizacaoBody.getFabricante()) &&
                imunizacaoBanco.getLote().equals(imunizacaoBody.getLote()) &&
                imunizacaoBanco.getLocalAplicacao().equals(imunizacaoBody.getLocalAplicacao()) &&
                imunizacaoBanco.getProfissionalAplicador().equals(imunizacaoBody.getProfissionalAplicador());
    }
}
