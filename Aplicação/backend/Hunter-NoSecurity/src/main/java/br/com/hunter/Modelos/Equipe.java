package br.com.hunter.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
public class Equipe {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "idEquipe")
    private Integer idEquipe;

    private String nomeEquipe;



    public Integer getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }


}
