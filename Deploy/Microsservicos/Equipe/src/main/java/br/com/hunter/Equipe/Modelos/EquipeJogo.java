package br.com.hunter.Equipe.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
public class EquipeJogo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idEquipeJogo ;

    @ManyToOne
    @JoinColumn(name = "idEquipe")
    private Equipe idEquipe;

    @ManyToOne
    @JoinColumn(name = "idJogo")
    private Jogo idJogo;

    public Integer getIdEquipeJogo() {
        return idEquipeJogo;
    }

    public void setIdEquipeJogo(Integer idEquipeJogo) {
        this.idEquipeJogo = idEquipeJogo;
    }

    public Equipe getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Equipe idEquipe) {
        this.idEquipe = idEquipe;
    }

    public Jogo getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
    }
}
