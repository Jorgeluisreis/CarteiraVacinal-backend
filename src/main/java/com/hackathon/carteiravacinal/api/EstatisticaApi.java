package com.hackathon.carteiravacinal.api;

import java.time.LocalDate;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackathon.carteiravacinal.service.EstatisticaService;
import com.hackathon.carteiravacinal.service.ImunizacaoService;
import com.hackathon.carteiravacinal.service.PacienteService;
import com.hackathon.carteiravacinal.service.VacinaService;
import com.hackathon.carteiravacinal.util.LocalDateAdapter;
import com.hackathon.carteiravacinal.exceptions.ApiException;
import com.hackathon.carteiravacinal.model.Paciente;

import spark.Request;
import spark.Response;
import spark.Route;

public class EstatisticaApi {
    private VacinaService vacinaService;
    private PacienteService pacienteService;
    private ImunizacaoService imunizacaoService;
    private EstatisticaService estatisticaService;
    private Gson gson;

    public EstatisticaApi(VacinaService vacinaService, PacienteService pacienteService,
            ImunizacaoService imunizacaoService, EstatisticaService estatisticaService) {
        this.vacinaService = vacinaService;
        this.pacienteService = pacienteService;
        this.imunizacaoService = imunizacaoService;
        this.estatisticaService = estatisticaService;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public Route qtdeVacinasAplicadasPorPaciente = (Request req, Response res) -> {
        try {
            String idPacienteParam = req.params(":id");

            if (idPacienteParam == null || !idPacienteParam.matches("\\d+")) {
                res.status(400);
                return "Erro: O ID do paciente deve ser um número inteiro.";
            }

            Long idPaciente = Long.parseLong(idPacienteParam);

            pacienteService.buscarPacientePorId(idPaciente);

            int quantidade = estatisticaService.contarVacinasAplicadasPorPaciente(idPaciente);

            res.status(200);
            return gson.toJson(Map.of("quantidade", quantidade));
        } catch (ApiException e) {
            res.status(500);
            return "Erro ao buscar a quantidade de vacinas aplicadas: " + e.getMessage();
        } catch (Exception e) {
            res.status(500);
            return "Erro inesperado: " + e.getMessage();
        }
    };

    public Route qtdeVacinasProximasPorPaciente = (Request req, Response res) -> {
        try {
            String idPacienteParam = req.params(":id");

            if (idPacienteParam == null || !idPacienteParam.matches("\\d+")) {
                res.status(400);
                return "Erro: O ID do paciente deve ser um número inteiro.";
            }

            Long idPaciente = Long.parseLong(idPacienteParam);
            Paciente paciente = pacienteService.buscarPacientePorId(idPaciente);

            if (paciente == null) {
                res.status(404);
                return "Erro: Paciente não encontrado.";
            }

            int quantidade = estatisticaService.qtdeVacinasProximasPorPaciente(paciente);

            res.status(200);
            return gson.toJson(Map.of("quantidade", quantidade));
        } catch (ApiException e) {
            res.status(500);
            return "Erro ao buscar a quantidade de vacinas para o próximo mês: " + e.getMessage();
        } catch (Exception e) {
            res.status(500);
            return "Erro inesperado: " + e.getMessage();
        }
    };

    public Route qtdeVacinasAtrasadasPorPaciente = (Request req, Response res) -> {
        try {
            String idPacienteParam = req.params(":id");

            if (idPacienteParam == null || !idPacienteParam.matches("\\d+")) {
                res.status(400);
                return "Erro: O ID do paciente deve ser um número inteiro.";
            }

            Long idPaciente = Long.parseLong(idPacienteParam);
            Paciente paciente = pacienteService.buscarPacientePorId(idPaciente);

            if (paciente == null) {
                res.status(404);
                return "Erro: Paciente não encontrado.";
            }

            int quantidade = estatisticaService.qtdeVacinasAtrasadasPorPaciente(paciente);

            res.status(200);
            return gson.toJson(Map.of("quantidade", quantidade));
        } catch (ApiException e) {
            res.status(500);
            return "Erro ao buscar a quantidade de vacinas atrasadas: " + e.getMessage();
        } catch (Exception e) {
            res.status(500);
            return "Erro inesperado: " + e.getMessage();
        }
    };

    public Route qtdeVacinasAcimaDeIdade = (Request req, Response res) -> {
        try {
            String mesesParam = req.params(":meses");

            if (mesesParam == null || !mesesParam.matches("\\d+")) {
                res.status(400);
                return "Erro: A idade em meses deve ser um número inteiro.";
            }

            int idadeMeses = Integer.parseInt(mesesParam);
            int quantidade = estatisticaService.qtdeVacinasAcimaDeIdade(idadeMeses);

            res.status(200);
            return gson.toJson(Map.of("quantidade", quantidade));
        } catch (ApiException e) {
            res.status(500);
            return "Erro ao buscar a quantidade de vacinas acima da idade informada: " + e.getMessage();
        } catch (Exception e) {
            res.status(500);
            return "Erro inesperado: " + e.getMessage();
        }
    };

    public Route qtdeVacinasNaoAplicaveis = (Request req, Response res) -> {
        try {
            String idPacienteParam = req.params(":id");

            if (idPacienteParam == null || !idPacienteParam.matches("\\d+")) {
                res.status(400);
                return "Erro: O ID do paciente deve ser um número inteiro.";
            }

            Long idPaciente = Long.parseLong(idPacienteParam);
            Paciente paciente = pacienteService.buscarPacientePorId(idPaciente);

            if (paciente == null) {
                res.status(404);
                return "Erro: Paciente não encontrado.";
            }

            int quantidade = estatisticaService.qtdeVacinasNaoAplicaveis(paciente);

            res.status(200);
            return gson.toJson(Map.of("quantidade", quantidade));
        } catch (ApiException e) {
            res.status(500);
            return "Erro ao buscar a quantidade de vacinas não aplicáveis: " + e.getMessage();
        } catch (Exception e) {
            res.status(500);
            return "Erro inesperado: " + e.getMessage();
        }
    };
}
