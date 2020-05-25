package br.com.hunter.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DynamicUpdate
public class StatusSolicitacao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idStatusSolicitacao;

    private String status;

    public Integer getIdStatusSolicitacao() {
        return idStatusSolicitacao;
    }

    public void setIdStatusSolicitacao(Integer idStatus) {
        this.idStatusSolicitacao = idStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

