package com.hackathon.carteiravacinal.model;

public class Vacina {
    private Long id;
    private String vacina;
    private String dose;
    private Integer idadeRecomendadaMeses;
    private String descricao;
    private Integer limiteAplicacao;
    private PublicoAlvo publicoAlvo;

    public enum PublicoAlvo {
        CRIANÇA, ADOLESCENTE, ADULTO, GESTANTE
    }

    public Vacina() {
    }

    public Vacina(Long id, String vacina, String dose, Integer idadeRecomendadaMeses, String descricao,
            Integer limiteAplicacao, PublicoAlvo publicoAlvo) {
        this.id = id;
        this.vacina = vacina;
        this.dose = dose;
        this.idadeRecomendadaMeses = idadeRecomendadaMeses;
        this.descricao = descricao;
        this.limiteAplicacao = limiteAplicacao;
        this.publicoAlvo = publicoAlvo;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVacina() {
        return vacina;
    }

    public void setVacina(String vacina) {
        this.vacina = vacina;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Integer getidadeRecomendadaMeses() {
        return idadeRecomendadaMeses;
    }

    public void setidadeRecomendadaMeses(Integer idadeRecomendadaMeses) {
        this.idadeRecomendadaMeses = idadeRecomendadaMeses;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLimiteAplicacao() {
        return limiteAplicacao;
    }

    public void setLimiteAplicacao(Integer limiteAplicacao) {
        this.limiteAplicacao = limiteAplicacao;
    }

    public PublicoAlvo getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(PublicoAlvo publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
}