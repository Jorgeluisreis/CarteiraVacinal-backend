package com.hackathon.carteiravacinal.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hackathon.carteiravacinal.model.Imunizacoes;
import com.hackathon.carteiravacinal.service.ImunizacaoService;
import com.hackathon.carteiravacinal.util.GsonUtil;
import com.hackathon.carteiravacinal.exceptions.ApiException;
import spark.Request;
import spark.Response;
import spark.Route;

public class ImunizacaoApi {

    private ImunizacaoService imunizacaoService;
    private Gson gson;

    public ImunizacaoApi(ImunizacaoService imunizacaoService) {
        this.imunizacaoService = imunizacaoService;
        this.gson = GsonUtil.getGson();
    }

    public Route adicionarImunizacao = (Request req, Response res) -> {
        try {
            Imunizacoes imunizacao = gson.fromJson(req.body(), Imunizacoes.class);

            if (imunizacao.getIdPaciente() == null) {
                res.status(400);
                return "Erro: O ID do paciente é obrigatório.";
            }
            if (imunizacao.getDataAplicacao() == null) {
                res.status(400);
                return "Erro: A data de aplicação é obrigatória.";
            }
            if (imunizacao.getIdDose() < 0) {
                res.status(400);
                return "Erro: O ID da Dose é obrigatório.";
            }

            Long codigoImunizacao = imunizacaoService.adicionarImunizacao(imunizacao);

            res.status(201);
            return "Imunização inserida com sucesso! Código: " + codigoImunizacao;
        } catch (JsonSyntaxException e) {
            res.status(400);
            return "Erro: Formato de dados inválido. " + e.getMessage();
        } catch (ApiException e) {
            res.status(400);
            return "Erro ao adicionar imunização: " + e.getMessage();
        } catch (Exception e) {
            res.status(500);
            return "Erro inesperado: " + e.getMessage();
        }
    };

    public Route alterarImunizacao = (Request req, Response res) -> {
        try {
            Long idImunizacaoRequisitada = Long.parseLong(req.params(":id"));
            Imunizacoes imunizacaoRequisitada = imunizacaoService.buscarImunizacaoPorId(idImunizacaoRequisitada);

            if (imunizacaoRequisitada == null) {
                res.status(404);
                return "Imunização não encontrada.";
            }

            Imunizacoes imunizacao = gson.fromJson(req.body(), Imunizacoes.class);

            if (isImunizacaoIgual(imunizacaoRequisitada, imunizacao)) {
                res.status(400);
                return "Erro: Não há dados a serem alterados, os dados enviados são iguais aos existentes no banco de dados.";
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
                    return "Imunização atualizada com sucesso!";
                } else {
                    res.status(500);
                    return "Erro ao atualizar imunização.";
                }
            } else {
                res.status(400);
                return "Erro: Não há dados a serem alterados, os dados enviados são iguais aos existentes no banco de dados.";
            }
        } catch (ApiException e) {
            res.status(400);
            return "Erro ao alterar imunização: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            res.status(400);
            return "Erro no formato de dados: " + e.getMessage();
        }
    };

    public Route excluirImunizacao = (Request req, Response res) -> {
        try {
            Long idImunizacaoRequisitada = Long.parseLong(req.params(":id"));

            imunizacaoService.buscarImunizacaoPorId(idImunizacaoRequisitada);

            boolean excluido = imunizacaoService.excluirImunizacao(idImunizacaoRequisitada);

            if (excluido) {
                res.status(200);
                return "Imunização excluída com sucesso!";
            } else {
                res.status(400);
                return "Erro ao excluir Imunização.";
            }
        } catch (ApiException e) {
            if (e.getMessage().equals("Imunização não encontrada com o ID fornecido.")) {
                res.status(404);
                return e.getMessage();
            }
            res.status(400);
            return "Erro ao excluir Imunização: " + e.getMessage();
        } catch (NumberFormatException e) {
            res.status(400);
            return "Erro no formato do ID.";
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