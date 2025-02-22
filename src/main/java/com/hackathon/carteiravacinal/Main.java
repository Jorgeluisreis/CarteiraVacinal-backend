package com.hackathon.carteiravacinal;

import com.hackathon.carteiravacinal.api.*;
import com.hackathon.carteiravacinal.repository.*;
import com.hackathon.carteiravacinal.service.*;
import com.hackathon.carteiravacinal.config.RouteConfig;
import com.hackathon.carteiravacinal.config.CorsConfig;
import spark.Spark;

public class Main {

    public static void main(String[] args) {
        int porta = 3300;

        Spark.port(porta);

        // Configuração do CORS
        CorsConfig.configurarCORS();

        // Paciente
        PacienteRepository pacienteRepository = new PacienteRepository();
        PacienteService pacienteService = new PacienteService(pacienteRepository);
        PacienteApi pacienteApi = new PacienteApi(pacienteService);

        // Imunizacoes
        ImunizacaoRepository imunizacaoRepository = new ImunizacaoRepository();
        ImunizacaoService imunizacaoService = new ImunizacaoService(imunizacaoRepository, pacienteRepository);
        ImunizacaoApi imunizacaoApi = new ImunizacaoApi(imunizacaoService, pacienteService);

        // Vacina
        VacinaRepository vacinaRepository = new VacinaRepository();
        VacinaService vacinaService = new VacinaService(vacinaRepository);
        VacinaApi vacinaApi = new VacinaApi(vacinaService, pacienteService);

        RouteConfig.configurarRotas(pacienteApi, imunizacaoApi, vacinaApi);

        System.out.printf("Servidor rodando na porta %d...\n", porta);
    }
}