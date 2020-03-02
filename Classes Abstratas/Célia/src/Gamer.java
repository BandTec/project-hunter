public abstract class Gamer {

        private String nome;
        private int inscricao;


    public Gamer(String nome, int inscricao) {
        this.nome = nome;
        this.inscricao = inscricao;
    }

    public abstract int calNivel();

    @Override
    public String toString() {
        return "Gamer{" +
                "nome='" + nome + '\'' +
                ", inscricao=" + inscricao +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public int getInscricao() {
        return inscricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInscricao(int inscricao) {
        this.inscricao = inscricao;
    }
}