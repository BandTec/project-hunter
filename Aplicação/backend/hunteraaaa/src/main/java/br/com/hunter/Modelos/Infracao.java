package br.com.hunter.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
public class Infracao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idInfracao;

    private String tipoInfracao;

    public Integer getIdInfracao() {
        return idInfracao;
    }

    public void setIdInfracao(Integer idInfracao) {
        this.idInfracao = idInfracao;
    }

    public String getTipoInfracao() {
        return tipoInfracao;
    }

    public void setTipoInfracao(String tipoInfracao) {
        this.tipoInfracao = tipoInfracao;
    }
}
