package br.com.hunter.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
public class EquipeGamer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idEquipeGamer;

    @ManyToOne
    @JoinColumn(name="idEquipe")
    private Equipe idEquipe;

    @ManyToOne
    @JoinColumn(name="idGamer")
    private Gamer idGamer;

    @ManyToOne
    @JoinColumn(name="idStatus")
    private StatusSolicitacao idStatus;

    public Integer getIdEquipeGamer() {
        return idEquipeGamer;
    }

    public void setIdEquipeGamer(Integer idEquipeGamer) {
        this.idEquipeGamer = idEquipeGamer;
    }

    public Equipe getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Equipe idEquipe) {
        this.idEquipe = idEquipe;
    }

    public Gamer getIdGamer() {
        return idGamer;
    }

    public void setIdGamer(Gamer idGamer) {
        this.idGamer = idGamer;
    }

    public StatusSolicitacao getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(StatusSolicitacao idStatus) {
        this.idStatus = idStatus;
    }
}
