package br.com.hunter.Partida.Modelos;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
public class Partida {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idPK;

    private Integer idPartida;

    @ManyToOne
    @JoinColumn(name="idJogo")
    private Jogo idJogo;

    @ManyToOne
    @JoinColumn(name="idEquipe", nullable = true)
    private Equipe idEquipe;

    @ManyToOne
    @JoinColumn(name="idGamer")
    private Gamer idGamer;

    @ManyToOne
    @JoinColumn(name="idPosicao")
    private Posicao idPosicao;

    @ManyToOne
    @JoinColumn(name="idInfracao")
    private Infracao idInfracao;

    private LocalDate data;

    private LocalTime hora;

    @Type(type = "numeric_boolean")
    @Column(columnDefinition = "default 0")
    private boolean winner;

    public Integer getIdPK() {
        return idPK;
    }

    public void setIdPK(Integer idPK) {
        this.idPK = idPK;
    }

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Jogo getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
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

    public Posicao getIdPosicao() {
        return idPosicao;
    }

    public void setIdPosicao(Posicao idPosicao) {
        this.idPosicao = idPosicao;
    }

    public Infracao getIdInfracao() {
        return idInfracao;
    }

    public void setIdInfracao(Infracao idInfracao) {
        this.idInfracao = idInfracao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
