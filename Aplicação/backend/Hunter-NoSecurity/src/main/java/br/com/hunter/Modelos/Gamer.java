package br.com.hunter.Modelos;



import javax.persistence.*;
import java.util.Collection;

@Entity
public class Gamer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "idGamer")
    private Integer idGamer;

    private String nome;

    private String cpf;

    private String email;

    private String senha;

    private String telefone;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Integer getIdGamer() {
        return idGamer;
    }

    public void setIdGamer(Integer idGamer) {
        this.idGamer = idGamer;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return this.email;
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }
}
