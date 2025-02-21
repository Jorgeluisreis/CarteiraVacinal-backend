package com.hackathon.carteiravacinal.service;

import com.hackathon.carteiravacinal.exceptions.ApiException;
import com.hackathon.carteiravacinal.model.Paciente;
import com.hackathon.carteiravacinal.repository.EstatisticaRepository;

public class EstatisticaService {

    private final EstatisticaRepository estatisticaRepository;

    public EstatisticaService(EstatisticaRepository estatisticaRepository) {
        this.estatisticaRepository = estatisticaRepository;
    }

    public int contarVacinasAplicadasPorPaciente(Long idPaciente)
            throws ApiException {
        try {
            return estatisticaRepository.contarVacinasAplicadasPorPaciente(idPaciente);
        } catch (Exception e) {
            throw new ApiException("Imunizações não encontradas.");
        }
    }

    public int qtdeVacinasProximasPorPaciente(Paciente Paciente)
            throws ApiException {
        try {
            return estatisticaRepository.qtdeVacinasProximasPorPaciente(Paciente);
        } catch (Exception e) {
            throw new ApiException("Imunizações não encontradas.");
        }
    }

    public int qtdeVacinasAtrasadasPorPaciente(Paciente paciente) throws ApiException {
        try {
            return estatisticaRepository.qtdeVacinasAtrasadasPorPaciente(paciente);
        } catch (Exception e) {
            throw new ApiException("Erro ao obter vacinas atrasadas.");
        }
    }

    public int qtdeVacinasAcimaDeIdade(int idadeMeses) throws ApiException {
        try {
            return estatisticaRepository.qtdeVacinasAcimaDeIdade(idadeMeses);
        } catch (Exception e) {
            throw new ApiException("Erro ao obter vacinas acima da idade informada.");
        }
    }

    public int qtdeVacinasNaoAplicaveis(Paciente paciente) throws ApiException {
        try {
            return estatisticaRepository.qtdeVacinasNaoAplicaveis(paciente);
        } catch (Exception e) {
            throw new ApiException("Erro ao buscar vacinas não aplicáveis.", e);
        }
    }
}
