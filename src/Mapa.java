public class Mapa {
    private Organismo[] organismos;
    private Alimento[] alimentos;
    private Casilla[][] matriz;


    public Mapa(){
        matriz = new Casilla[50][50];
        alimentos = null;
        organismos = null;
    }

    public Casilla[][] getDimension(){
        return matriz;
    }
}
