package b.rcom.hunter.Hunter.Modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Infracoes {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idInfracoes;

    private Gamer fkGamer;

    private DadosPartida fkPartida;

    private TipoInfracao fkTipo;

    public Integer getIdInfracoes() {
        return idInfracoes;
    }

    public void setIdInfracoes(Integer idInfracoes) {
        this.idInfracoes = idInfracoes;
    }

    public Gamer getFkGamer() {
        return fkGamer;
    }

    public void setFkGamer(Gamer fkGamer) {
        this.fkGamer = fkGamer;
    }

    public DadosPartida getFkPartida() {
        return fkPartida;
    }

    public void setFkPartida(DadosPartida fkPartida) {
        this.fkPartida = fkPartida;
    }

    public TipoInfracao getFkTipo() {
        return fkTipo;
    }

    public void setFkTipo(TipoInfracao fkTipo) {
        this.fkTipo = fkTipo;
    }
}
