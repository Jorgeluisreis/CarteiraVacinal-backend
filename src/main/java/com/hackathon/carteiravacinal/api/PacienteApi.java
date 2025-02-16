package com.hackathon.carteiravacinal.api;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackathon.carteiravacinal.model.Paciente;
import com.hackathon.carteiravacinal.service.PacienteService;
import com.hackathon.carteiravacinal.exceptions.ApiException;
import spark.Request;
import spark.Response;
import spark.Route;
import com.hackathon.carteiravacinal.util.LocalDateAdapter;

public class PacienteApi {

    private PacienteService pacienteService;
    private Gson gson;

    public PacienteApi(PacienteService pacienteService) {
        this.pacienteService = pacienteService;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public Route adicionarPaciente = (Request req, Response res) -> {
        try {
            Paciente paciente = gson.fromJson(req.body(), Paciente.class);

            if (paciente.getSexo() == null) {
                res.status(400);
                return "Sexo inválido. Escolha entre 'M' ou 'F'.";
            }

            Long idPaciente = pacienteService.adicionarPaciente(paciente);

            res.status(201);

            return "Paciente inserido com sucesso! ID: " + idPaciente;
        } catch (ApiException e) {
            res.status(400);
            return "Erro ao adicionar paciente: " + e.getMessage();
        }
    };
}