
import java.util.ArrayList;

public class Equipe {

    private ArrayList<Gamer> lista;

    public Equipe() {
        lista = new ArrayList<Gamer>();
    }

    public void adcionarGamer(Gamer g) {
        lista.add(g);
    }

    public void exibeGamer() {
            System.out.println("\nGamers:");
            for (Gamer g : lista) {
                System.out.println(g);
            }

    }

}
