package br.com.hunter.Modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class DadosPartida {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idDadosPartida;

    private Integer nota;

    private Date Data;

    private Timestamp Horario;

    public Integer getIdDadosPartida() {
        return idDadosPartida;
    }

    public void setIdDadosPartida(Integer idDadosPartida) {
        this.idDadosPartida = idDadosPartida;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public Timestamp getHorario() {
        return Horario;
    }

    public void setHorario(Timestamp horario) {
        Horario = horario;
    }
}
