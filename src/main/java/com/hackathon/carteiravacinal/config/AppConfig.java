package com.hackathon.carteiravacinal.config;

import com.hackathon.carteiravacinal.api.*;
import com.hackathon.carteiravacinal.repository.*;
import com.hackathon.carteiravacinal.service.*;

public class AppConfig {

    private final PacienteRepository pacienteRepository;
    private final PacienteService pacienteService;
    private final PacienteApi pacienteApi;

    private final ImunizacaoRepository imunizacaoRepository;
    private final ImunizacaoService imunizacaoService;
    private final ImunizacaoApi imunizacaoApi;

    private final VacinaRepository vacinaRepository;
    private final VacinaService vacinaService;
    private final VacinaApi vacinaApi;

    private final EstatisticaRepository estatisticaRepository;
    private final EstatisticaService estatisticaService;
    private final EstatisticaApi estatisticaApi;

    public AppConfig() {
        // Paciente
        this.pacienteRepository = new PacienteRepository();
        this.pacienteService = new PacienteService(pacienteRepository);
        this.pacienteApi = new PacienteApi(pacienteService);

        // Imunização
        this.imunizacaoRepository = new ImunizacaoRepository();
        this.imunizacaoService = new ImunizacaoService(imunizacaoRepository, pacienteRepository);
        this.imunizacaoApi = new ImunizacaoApi(imunizacaoService, pacienteService);

        // Vacina
        this.vacinaRepository = new VacinaRepository();
        this.vacinaService = new VacinaService(vacinaRepository);
        this.vacinaApi = new VacinaApi(vacinaService, pacienteService);

        // Estatística
        this.estatisticaRepository = new EstatisticaRepository();
        this.estatisticaService = new EstatisticaService(estatisticaRepository);
        this.estatisticaApi = new EstatisticaApi(vacinaService, pacienteService, estatisticaService);
    }

    public PacienteApi getPacienteApi() {
        return pacienteApi;
    }

    public ImunizacaoApi getImunizacaoApi() {
        return imunizacaoApi;
    }

    public VacinaApi getVacinaApi() {
        return vacinaApi;
    }

    public EstatisticaApi getEstatisticaApi() {
        return estatisticaApi;
    }
}