package br.com.hunter.Gamer.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
public class GamerInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idGamerInfo;

    @ManyToOne
    @JoinColumn(name="idGamer")
    private Gamer idGamer;

    @ManyToOne
    @JoinColumn(name="idJogo")
    private  Jogo idJogo;

    @ManyToOne
    @JoinColumn(name="idPosicao")
    private Posicao idPosicao;

    public Integer getIdGamerInfo() {
        return idGamerInfo;
    }

    public void setIdGamerInfo(Integer idGamerInfo) {
        this.idGamerInfo = idGamerInfo;
    }

    public Gamer getIdGamer() {
        return idGamer;
    }

    public void setIdGamer(Gamer idGamer) {
        this.idGamer = idGamer;
    }

    public Jogo getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
    }

    public Posicao getIdPosicao() {
        return idPosicao;
    }

    public void setIdPosicao(Posicao idPosicao) {
        this.idPosicao = idPosicao;
    }
}
