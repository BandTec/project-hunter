public class Profissional extends Gamer{

    private int nivel;

    public Profissional(String nome, int inscricao, int nivel) {
        super(nome, inscricao);
        this.nivel = nivel;
    }

    @Override
    public int calNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return "Profissional{" +
                "Nome: " + getNome()+
                "Inscrição: " +getInscricao()+
                "nivel=" + nivel +
                '}';
    }
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
