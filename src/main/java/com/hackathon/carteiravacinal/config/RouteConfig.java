package com.hackathon.carteiravacinal.config;

import com.hackathon.carteiravacinal.api.*;
import spark.Spark;

public class RouteConfig {

    public static void configurarRotas(PacienteApi pacienteApi, ImunizacaoApi imunizacaoApi, VacinaApi vacinaApi) {
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
        Spark.get("/imunizacao/consultar/:id", imunizacaoApi.consultarImunizacaoPorIdImunizacao);
        Spark.get("/imunizacao/consultar/paciente/:id", imunizacaoApi.consultarImunizacaoPorIdPaciente);
        Spark.get("imunizacao/consultar/paciente/:id/aplicacao/:dt_ini/:dt_fim",
                imunizacaoApi.consultarImunizacaoPorIdeIntervaloAplicacao);

        Spark.get("/vacinas/consultar", vacinaApi.consultarTodasVacinas);
        Spark.get("/vacinas/consultar/faixa_etaria/:faixa", vacinaApi.consultarTodasVacinasPorFaixaEtaria);
        Spark.get("/vacinas/consultar/idade_maior/:meses", vacinaApi.consultarTodasVacinasRecomendadasAcimaIdade);
    }
}