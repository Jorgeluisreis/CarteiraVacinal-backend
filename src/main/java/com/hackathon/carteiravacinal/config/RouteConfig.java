package com.hackathon.carteiravacinal.config;

import com.hackathon.carteiravacinal.api.PacienteApi;
import spark.Spark;

public class RouteConfig {

    public static void configurarRotas(PacienteApi pacienteApi) {
        Spark.post("/paciente/inserir", pacienteApi.adicionarPaciente);
        Spark.put("/paciente/alterar/:id", pacienteApi.alterarPaciente);
    }
}