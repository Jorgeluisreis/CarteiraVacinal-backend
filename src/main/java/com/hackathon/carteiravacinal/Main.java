package com.hackathon.carteiravacinal;

import com.hackathon.carteiravacinal.api.*;
import com.hackathon.carteiravacinal.repository.*;
import com.hackathon.carteiravacinal.service.*;
import com.hackathon.carteiravacinal.config.RouteConfig;
import spark.Spark;

public class Main {

    public static void main(String[] args) {
        int porta = 3300;

        Spark.port(porta);

        // Paciente
        PacienteRepository pacienteRepository = new PacienteRepository();
        PacienteService pacienteService = new PacienteService(pacienteRepository);
        PacienteApi pacienteApi = new PacienteApi(pacienteService);

        // Imunizacoes
        ImunizacaoRepository imunizacaoRepository = new ImunizacaoRepository();
        ImunizacaoService imunizacaoService = new ImunizacaoService(imunizacaoRepository, pacienteRepository);
        ImunizacaoApi imunizacaoApi = new ImunizacaoApi(imunizacaoService, pacienteService);

        RouteConfig.configurarRotas(pacienteApi, imunizacaoApi);

        System.out.printf("Servidor rodando na porta %d...\n", porta);
    }
}