//package b.rcom.hunter.Hunter.Modelos;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class Infracoes {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Integer idInfracoes;
//
//    private Integer fkGamer;
//
//    private Integer fkPartida;
//
//    private Integer fkInfracao;
//
//    public Infracoes(Integer idInfracoes, Integer fkGamer, Integer fkPartida, Integer fkInfracao) {
//        this.idInfracoes = idInfracoes;
//        this.fkGamer = fkGamer;
//        this.fkPartida = fkPartida;
//        this.fkInfracao = fkInfracao;
//    }
//
//    public Integer getIdInfracoes() {
//        return idInfracoes;
//    }
//
//    public void setIdInfracoes(Integer idInfracoes) {
//        this.idInfracoes = idInfracoes;
//    }
//
//    public Integer getFkGamer() {
//        return fkGamer;
//    }
//
//    public void setFkGamer(Integer fkGamer) {
//        this.fkGamer = fkGamer;
//    }
//
//    public Integer getFkPartida() {
//        return fkPartida;
//    }
//
//    public void setFkPartida(Integer fkPartida) {
//        this.fkPartida = fkPartida;
//    }
//
//    public Integer getFkInfracao() {
//        return fkInfracao;
//    }
//
//    public void setFkInfracao(Integer fkInfracao) {
//        this.fkInfracao = fkInfracao;
//    }
//}
