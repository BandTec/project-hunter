public class JogadorIntermediario extends Gamer{

        private int nivel;

    public JogadorIntermediario(String nome, int inscricao, int nivel) {
        super(nome, inscricao);
        this.nivel = nivel;
    }

    @Override
    public int calNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return "JogadorIntermediario{" +
                "Nome: " + getNome()  +
                "Inscrição: " +getInscricao()  +
                "nivel= " + nivel +
                '}';
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}