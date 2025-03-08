package com.hackathon.carteiravacinal;

import com.hackathon.carteiravacinal.config.*;

import spark.Spark;

public class Main {
    public static void main(String[] args) {
        int porta = 3300;
        Spark.port(porta);

        CorsConfig.configurarCORS();

        AppConfig appConfig = new AppConfig();

        RouteConfig.configurarRotas(
                appConfig.getPacienteApi(),
                appConfig.getImunizacaoApi(),
                appConfig.getVacinaApi(),
                appConfig.getEstatisticaApi());

        System.out.printf("Servidor rodando na porta %d...\n", porta);
    }
}