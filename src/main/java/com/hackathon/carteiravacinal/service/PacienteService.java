package com.hackathon.carteiravacinal.service;

import com.hackathon.carteiravacinal.model.Paciente;
import com.hackathon.carteiravacinal.repository.PacienteRepository;
import com.hackathon.carteiravacinal.exceptions.ApiException;

public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Long adicionarPaciente(Paciente paciente) throws ApiException {
        try {
            return pacienteRepository.adicionarPaciente(paciente);
        } catch (Exception e) {
            throw new ApiException("Falha ao inserir paciente no banco de dados.");
        }
    }

    public boolean alterarPaciente(Long idPaciente, Paciente paciente) throws ApiException {
        try {
            return pacienteRepository.alterarPaciente(idPaciente, paciente);
        } catch (Exception e) {
            throw new ApiException("Falha ao alterar o paciente.");
        }
    }

    public Paciente buscarPacientePorId(Long id) throws ApiException {
        try {
            return pacienteRepository.buscarPacientePorId(id);
        } catch (Exception e) {
            throw new ApiException("Falha ao buscar paciente no banco de dados.");
        }
    }

    public boolean verificarIdPaciente(Long idPacienteRequisitado, Long idPacienteAlterado) {
        return idPacienteRequisitado.equals(idPacienteAlterado);
    }
}