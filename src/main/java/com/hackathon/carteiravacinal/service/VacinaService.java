package com.hackathon.carteiravacinal.service;

import java.util.List;

import com.hackathon.carteiravacinal.exceptions.ApiException;
import com.hackathon.carteiravacinal.model.Vacina;
import com.hackathon.carteiravacinal.repository.VacinaRepository;

public class VacinaService {

    private final VacinaRepository vacinaRepository;

    public VacinaService(VacinaRepository vacinaRepository) {
        this.vacinaRepository = vacinaRepository;
    }

    public List<Vacina> consultarTodasVacinas() throws ApiException {
        return vacinaRepository.consultarTodasVacinas();
    }

    public List<Vacina> consultarTodasVacinasPorFaixaEtaria(Vacina.PublicoAlvo faixaEtaria) throws ApiException {
        return vacinaRepository.consultarTodasVacinasPorFaixaEtaria(faixaEtaria);
    }

    public List<Vacina> consultarTodasVacinasRecomendadasAcimaIdade(int meses) throws ApiException {
        return vacinaRepository.consultarTodasVacinasRecomendadasAcimaIdade(meses);
    }
}
