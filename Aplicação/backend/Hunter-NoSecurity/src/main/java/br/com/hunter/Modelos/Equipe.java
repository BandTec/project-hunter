package br.com.hunter.Modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idEquipe;

    private String nome_equipe;

    private String pais;

    private String regiao;

//    public Equipe(Integer idEquipe, String nomeEquipe) {
//        this.idEquipe = idEquipe;
//        this.nomeEquipe = nomeEquipe;
//    }

    public Integer getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomeEquipe() {
        return nome_equipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nome_equipe = nomeEquipe;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}
