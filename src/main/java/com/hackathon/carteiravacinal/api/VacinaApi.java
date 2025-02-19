package com.hackathon.carteiravacinal.api;

import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackathon.carteiravacinal.exceptions.ApiException;
import com.hackathon.carteiravacinal.model.Vacina;
import com.hackathon.carteiravacinal.service.VacinaService;
import com.hackathon.carteiravacinal.util.LocalDateAdapter;

import spark.Request;
import spark.Response;
import spark.Route;

public class VacinaApi {
    private VacinaService vacinaService;
    private Gson gson;

    public VacinaApi(VacinaService vacinaService) {
        this.vacinaService = vacinaService;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public Route consultarTodasVacinas = (Request req, Response res) -> {
        try {
            List<Vacina> vacinas = vacinaService.consultarTodasVacinas();
            res.status(200);
            return gson.toJson(vacinas);
        } catch (ApiException e) {
            res.status(400);
            return "Erro ao listar vacinas: " + e.getMessage();
        }
    };

    public Route consultarTodasVacinasPorFaixaEtaria = (Request req, Response res) -> {
        try {
            String faixaEtariaStr = req.params(":faixa").toUpperCase();

            Vacina.PublicoAlvo faixaEtaria;
            try {
                faixaEtaria = Vacina.PublicoAlvo.valueOf(faixaEtariaStr);
            } catch (IllegalArgumentException e) {
                res.status(400);
                return "Faixa etária inválida. Utilize: CRIANÇA, ADOLESCENTE, ADULTO ou GESTANTE.";
            }

            List<Vacina> vacinas = vacinaService.consultarTodasVacinasPorFaixaEtaria(faixaEtaria);

            if (vacinas.isEmpty()) {
                res.status(404);
                return "Nenhuma vacina encontrada para a faixa etária: " + faixaEtariaStr;
            }

            res.status(200);
            return gson.toJson(vacinas);
        } catch (ApiException e) {
            res.status(500);
            return "Erro ao listar vacinas: " + e.getMessage();
        }
    };

}
