package br.com.hunter.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DynamicUpdate
public class Posicao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idPosicao;

    private String posicao;

    public Integer getIdPosicao() {
        return idPosicao;
    }

    public void setIdPosicao(Integer idPosicao) {
        this.idPosicao = idPosicao;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }
}
