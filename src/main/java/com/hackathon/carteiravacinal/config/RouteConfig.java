package com.hackathon.carteiravacinal.config;

import com.hackathon.carteiravacinal.api.*;
import spark.Spark;

public class RouteConfig {

    public static void configurarRotas(PacienteApi pacienteApi, ImunizacaoApi imunizacaoApi) {
        Spark.post("/paciente/inserir", pacienteApi.adicionarPaciente);
        Spark.put("/paciente/alterar/:id", pacienteApi.alterarPaciente);
        Spark.delete("/paciente/excluir/:id", pacienteApi.excluirPaciente);
        Spark.get("/paciente/consultar", pacienteApi.consultarTodosPacientes);
        Spark.get("/paciente/consultar/:id", pacienteApi.consultarPacientePorId);

        Spark.post("/imunizacao/inserir", imunizacaoApi.adicionarImunizacao);
        Spark.put("/imunizacao/alterar/:id", imunizacaoApi.alterarImunizacao);
        Spark.delete("/imunizacao/excluir/:id", imunizacaoApi.excluirImunizacao);
        Spark.delete("/imunizacao/excluir/paciente/:id", imunizacaoApi.excluirTodasImunizacoesPaciente);
        Spark.get("/imunizacao/consultar", imunizacaoApi.consultarTodasImunizacoes);
    }
}