package com.hackathon.carteiravacinal;

import com.hackathon.carteiravacinal.api.ImunizacaoApi;
import com.hackathon.carteiravacinal.api.PacienteApi;
import com.hackathon.carteiravacinal.repository.ImunizacaoRepository;
import com.hackathon.carteiravacinal.repository.PacienteRepository;
import com.hackathon.carteiravacinal.service.ImunizacaoService;
import com.hackathon.carteiravacinal.service.PacienteService;
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