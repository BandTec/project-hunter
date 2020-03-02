public class TesteGamer {

    public static void main(String[] args) {
        JogadorIniciante i = new JogadorIniciante("Gustavo Henrique  ",   121132131 ,1);
        JogadorIntermediario m = new JogadorIntermediario("Gustavo Uesso  ", 9898020 , 5);
        Profissional p = new Profissional("Henrique Souza  ", 2125541 , 10);
        Experiente e = new Experiente("Vinícius de Oliveira  ", 777872177 , 20);


        Equipe team = new Equipe();

        team.adcionarGamer(i);
        team.adcionarGamer(m);
        team.adcionarGamer(p);
        team.adcionarGamer(e);

        team.exibeGamer();





    }


}
