package br.com.hunter.Modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipoInfracao {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idTIpoInfracao;

    private String tipoInfracao;

    public Integer getIdTIpoInfracao() {
        return idTIpoInfracao;
    }

    public void setIdTIpoInfracao(Integer idTIpoInfracao) {
        this.idTIpoInfracao = idTIpoInfracao;
    }

    public String getTipoInfracao() {
        return tipoInfracao;
    }

    public void setTipoInfracao(String tipoInfracao) {
        this.tipoInfracao = tipoInfracao;
    }
}
