package b.rcom.hunter.Hunter;


import java.util.Collection;


public class Gamer  {


    private String idGamer;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private boolean autenticado;


    public Gamer(String nome, String cpf, String email, String senha, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Gamer(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Gamer() {
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

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutentitcado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
