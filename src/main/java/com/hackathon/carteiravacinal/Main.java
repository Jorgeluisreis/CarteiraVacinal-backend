package com.hackathon.carteiravacinal;

import com.hackathon.carteiravacinal.api.PacienteApi;
import com.hackathon.carteiravacinal.repository.PacienteRepository;
import com.hackathon.carteiravacinal.service.PacienteService;
import com.hackathon.carteiravacinal.config.RouteConfig;
import spark.Spark;

public class Main {

    public static void main(String[] args) {
        int porta = 3000;

        Spark.port(porta);

        PacienteRepository pacienteRepository = new PacienteRepository();
        PacienteService pacienteService = new PacienteService(pacienteRepository);

        PacienteApi pacienteApi = new PacienteApi(pacienteService);

        RouteConfig.configurarRotas(pacienteApi);

        System.out.printf("Servidor rodando na porta %d...\n", porta);
    }
}