package com.hackathon.carteiravacinal.service;

import com.hackathon.carteiravacinal.exceptions.ApiException;
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

}
