public class Gamer {

    private String nome;
    private String email;
    private String senha;
    private String jogo;
    private String apelido;

    public Gamer(String nome, String email, String senha, String jogo, String apelido) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.jogo = jogo;
        this.apelido = apelido;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public void setApelido(String apelido) {
        this.nome = apelido;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getJogo() {
        return jogo;
    }

    public String getApelido() {
        return apelido;
    }

    @Override
    public String toString() {
        return "Gamer{" +
                "nome='" + nome + '\'' +
                "apelido='" + apelido + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", jogo='" + jogo + '\'' +
                '}';
    }
}

