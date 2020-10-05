package br.com.hunter.Modelos;
//
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//
//@Entity
//@DynamicUpdate
//public class GamerAmigo {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Integer idGamerInfo;
//
//    @ManyToOne
//    @JoinColumn(name="idGamer")
//    private Gamer idGamer;
//
//    @ManyToOne
//    @JoinColumn(name="idAmigo")
//    private  Gamer idAmigo;
//
//    @ManyToOne
//    @JoinColumn(name="idStatus")
//    private StatusSolicitacao idStatus;
//
//    public Integer getIdGamerInfo() {
//        return idGamerInfo;
//    }
//
//    public void setIdGamerInfo(Integer idGamerInfo) {
//        this.idGamerInfo = idGamerInfo;
//    }
//
//    public Gamer getIdGamer() {
//        return idGamer;
//    }
//
//    public void setIdGamer(Gamer idGamer) {
//        this.idGamer = idGamer;
//    }
//
//    public Gamer getIdAmigo() {
//        return idAmigo;
//    }
//
//    public void setIdAmigo(Gamer idAmigo) {
//        this.idAmigo = idAmigo;
//    }
//
//    public StatusSolicitacao getIdStatus() {
//        return idStatus;
//    }
//
//    public void setIdStatus(StatusSolicitacao idStatus) {
//        this.idStatus = idStatus;
//    }
//}
