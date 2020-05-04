package br.com.hunter.Modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idjogo;

    private String nomeJogo;

    private Integer atdPlayers;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private String tipo;

    public Integer getIdjogo() {
        return idjogo;
    }

    public void setIdjogo(Integer idjogo) {
        this.idjogo = idjogo;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public Integer getAtdPlayers() {
        return atdPlayers;
    }

    public void setAtdPlayers(Integer atdPlayers) {
        this.atdPlayers = atdPlayers;
    }
}
