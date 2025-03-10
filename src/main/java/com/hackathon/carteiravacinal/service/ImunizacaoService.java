package com.hackathon.carteiravacinal.service;

import com.hackathon.carteiravacinal.model.Imunizacoes;
import com.hackathon.carteiravacinal.model.Paciente;
import com.hackathon.carteiravacinal.repository.ImunizacaoRepository;
import com.hackathon.carteiravacinal.repository.PacienteRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.hackathon.carteiravacinal.exceptions.ApiException;

public class ImunizacaoService {

    private final ImunizacaoRepository imunizacaoRepository;
    private final PacienteRepository pacienteRepository;

    public ImunizacaoService(ImunizacaoRepository imunizacaoRepository, PacienteRepository pacienteRepository) {
        this.imunizacaoRepository = imunizacaoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public Imunizacoes buscarImunizacaoPorId(Long id) throws ApiException {
        Imunizacoes imunizacao = imunizacaoRepository.buscarImunizacaoPorId(id);
        if (imunizacao == null) {
            throw new ApiException("Imunização não encontrada.");
        }
        return imunizacao;
    }

    public boolean alterarImunizacao(Long id, Imunizacoes novaImunizacao) throws ApiException {
        return imunizacaoRepository.alterarImunizacao(id, novaImunizacao);
    }

    public boolean excluirImunizacao(Long idImunizacao) throws ApiException {
        try {
            Imunizacoes imunizacao = imunizacaoRepository.buscarImunizacaoPorId(idImunizacao);
            if (imunizacao == null) {
                throw new ApiException("Imunização não encontrada com o ID fornecido.");
            }
            return imunizacaoRepository.excluirImunizacao(idImunizacao);
        } catch (Exception e) {
            throw new ApiException("Falha ao excluir a Imunização.");
        }
    }

    public boolean excluirTodasImunizacoesPaciente(Long idPaciente) throws ApiException {
        try {
            Paciente paciente = pacienteRepository.buscarPacientePorId(idPaciente);

            if (paciente == null) {
                throw new ApiException("Paciente não encontrado com o ID fornecido.");
            }
            return imunizacaoRepository.excluirTodasImunizacoesPaciente(idPaciente);
        } catch (Exception e) {
            throw new ApiException("Falha ao excluir a Imunização.");
        }
    }

    public Long adicionarImunizacao(Imunizacoes imunizacao) throws ApiException, SQLException {

        Paciente paciente = pacienteRepository.buscarPacientePorId(imunizacao.getIdPaciente());
        if (paciente == null) {
            throw new ApiException("Paciente com ID " + imunizacao.getIdPaciente() + " não encontrado.");
        }

        boolean existe = imunizacaoRepository.verificarImunizacaoExistente(paciente.getId(), imunizacao.getIdDose());
        if (existe) {
            throw new ApiException("Este paciente já possui esta imunização cadastrada.");
        }

        try {
            return imunizacaoRepository.adicionarImunizacao(imunizacao);
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new ApiException("Este paciente já possui esta imunização cadastrada.");
            }
            throw new ApiException("Erro ao inserir imunização no banco de dados: " + e.getMessage());
        }
    }

    public List<Imunizacoes> consultarTodasImunizacoes() throws ApiException {
        return imunizacaoRepository.consultarTodasImunizacoes();
    }

    public List<Imunizacoes> consultarImunizacaoPorIdImunizacao(Long id) throws ApiException {
        try {
            return imunizacaoRepository.consultarImunizacaoPorIdImunizacao(id);
        } catch (Exception e) {
            throw new ApiException("Imunização não encontrado.");
        }
    }

    public List<Imunizacoes> consultarImunizacaoPorIdPaciente(Long id) throws ApiException {
        try {
            return imunizacaoRepository.consultarImunizacaoPorIdPaciente(id);
        } catch (Exception e) {
            throw new ApiException("Imunização não encontrado.");
        }
    }

    public List<Imunizacoes> consultarImunizacaoPorIdeIntervaloAplicacao(Long id, LocalDate dtIni, LocalDate dtFim)
            throws ApiException {
        try {
            return imunizacaoRepository.consultarImunizacaoPorIdeIntervaloAplicacao(id, dtIni, dtFim);
        } catch (Exception e) {
            throw new ApiException("Período de imunização não encontrado.");
        }
    }
}