package com.hackathon.carteiravacinal.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

            if (paciente.getDataNascimento() != null) {
                String dataNascimentoStr = paciente.getDataNascimento()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                if (!isValidDateFormat(dataNascimentoStr)) {
                    res.status(400);
                    res.type("application/json");
                    return gson.toJson("Erro no formato de data: a data deve ser no formato dd-MM-yyyy.");
                }
            }

            if (paciente.getSexo() == null) {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Sexo inválido. Escolha entre 'M' ou 'F'.");
            }

            if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Nome inválido. O nome não pode ser vazio.");
            }

            if (paciente.getCpf() != null
                    && (paciente.getCpf().length() != 11 || !paciente.getCpf().matches("\\d{11}"))) {
                res.status(400);
                res.type("application/json");
                return gson.toJson("CPF inválido. O CPF deve ter 11 dígitos numéricos.");
            }

            Long idPaciente = pacienteService.adicionarPaciente(paciente);

            res.status(201);
            res.type("application/json");
            return gson.toJson("Paciente inserido com sucesso! ID: " + idPaciente);
        } catch (ApiException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro ao adicionar paciente: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro no formato de data: " + e.getMessage());
        }
    };

    public Route alterarPaciente = (Request req, Response res) -> {
        try {
            Long idPacienteRequisitado = Long.parseLong(req.params(":id"));
            Paciente pacienteRequisitado = pacienteService.buscarPacientePorId(idPacienteRequisitado);

            if (pacienteRequisitado == null) {
                res.status(404);
                res.type("application/json");
                return gson.toJson("Paciente não encontrado.");
            }

            Paciente paciente = gson.fromJson(req.body(), Paciente.class);

            if (isPacienteIgual(pacienteRequisitado, paciente)) {
                res.status(400);
                res.type("application/json");
                return gson.toJson(
                        "Erro: Não há dados a serem alterados, os dados enviados são iguais aos existentes no banco de dados.");
            }

            Paciente pacienteAlterado = pacienteRequisitado;
            boolean dadosAlterados = false;

            if (!paciente.getNome().equals(pacienteRequisitado.getNome())) {
                pacienteAlterado.setNome(paciente.getNome());
                dadosAlterados = true;
            }
            if (!paciente.getCpf().equals(pacienteRequisitado.getCpf())) {
                pacienteAlterado.setCpf(paciente.getCpf());
                dadosAlterados = true;
            }
            if (!paciente.getSexo().equals(pacienteRequisitado.getSexo())) {
                pacienteAlterado.setSexo(paciente.getSexo());
                dadosAlterados = true;
            }
            if (!paciente.getDataNascimento().equals(pacienteRequisitado.getDataNascimento())) {
                pacienteAlterado.setDataNascimento(paciente.getDataNascimento());
                dadosAlterados = true;
            }

            if (dadosAlterados) {
                boolean atualizado = pacienteService.alterarPaciente(idPacienteRequisitado, pacienteAlterado);
                if (atualizado) {
                    res.status(200);
                    res.type("application/json");
                    return gson.toJson("Paciente atualizado com sucesso!");
                } else {
                    res.status(404);
                    res.type("application/json");
                    return gson.toJson("Erro ao atualizar paciente.");
                }
            } else {
                res.status(400);
                res.type("application/json");
                return gson.toJson(
                        "Erro: Não há dados a serem alterados, os dados enviados são iguais aos existentes no banco de dados.");
            }
        } catch (ApiException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson(e.getMessage());
        } catch (IllegalArgumentException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro no formato de dados: " + e.getMessage());
        }
    };

    public Route excluirPaciente = (Request req, Response res) -> {
        try {
            Long idPacienteRequisitado = Long.parseLong(req.params(":id"));

            pacienteService.buscarPacientePorId(idPacienteRequisitado);

            boolean excluido = pacienteService.excluirPaciente(idPacienteRequisitado);

            if (excluido) {
                res.status(200);
                res.type("application/json");
                return gson.toJson("Paciente excluído com sucesso!");
            } else {
                res.status(400);
                res.type("application/json");
                return gson.toJson("Erro ao excluir paciente.");
            }
        } catch (ApiException e) {
            if (e.getMessage().equals("Paciente não encontrado com o ID fornecido.")) {
                res.status(404);
            } else if (e.getMessage().contains("imunizações cadastradas")) {
                res.status(409); // Código HTTP 409 (Conflict) para esse caso específico
            } else {
                res.status(400);
            }
            res.type("application/json");
            return gson.toJson(e.getMessage());
        } catch (NumberFormatException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro no formato do ID.");
        }
    };

    public Route consultarTodosPacientes = (Request req, Response res) -> {
        try {
            List<Paciente> pacientes = pacienteService.consultarTodosPacientes();
            res.status(200);
            res.type("application/json");
            return gson.toJson(pacientes);
        } catch (ApiException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("Erro ao listar pacientes: " + e.getMessage());
        }
    };

    public Route consultarPacientePorId = (Request req, Response res) -> {
        try {
            Long idPaciente = Long.parseLong(req.params(":id"));
            Paciente paciente = pacienteService.buscarPacientePorId(idPaciente);

            if (paciente == null) {
                res.status(404);
                res.type("application/json");
                return gson.toJson("Paciente não encontrado.");
            }

            res.status(200);
            res.type("application/json");
            return gson.toJson(paciente);
        } catch (NumberFormatException e) {
            res.status(400);
            res.type("application/json");
            return gson.toJson("ID inválido. O ID deve ser um número.");
        } catch (ApiException e) {
            res.status(500);
            res.type("application/json");
            return gson.toJson("Erro ao consultar paciente: " + e.getMessage());
        }
    };

    private boolean isPacienteIgual(Paciente pacienteBanco, Paciente pacienteBody) {
        return pacienteBanco.getNome().equals(pacienteBody.getNome()) &&
                pacienteBanco.getCpf().equals(pacienteBody.getCpf()) &&
                pacienteBanco.getSexo().equals(pacienteBody.getSexo()) &&
                pacienteBanco.getDataNascimento().equals(pacienteBody.getDataNascimento());
    }

    private boolean isValidDateFormat(String data) {
        if (!data.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            return false;
        }
        return true;
    }
}