public class Mapa {
    private Organismo[] organismos;
    private Alimento[] alimentos;
    private Casilla[][] matriz;
    private Casilla casilla;
    private double valorAleatorio;
    private int posX;
    private int posY;

    public Mapa(){
        matriz = new Casilla[50][50];
        alimentos = new Alimento[10];
        organismos = new Organismo[10];

        //Inicializando cada Casilla en la matriz
        for (int i = 0; i < 50; i++){
            for(int j = 0; j < 50; j++){
                casilla = new Casilla();
                matriz[i][j] = casilla;
            }
        }

        //Inicializando los organismos
        organismos[0] = new OrganismoJugador();
        for(int i = 1; i < 10; i++){
            valorAleatorio = Math.random()*(1-0)+0;
            if(valorAleatorio < 0.5){
                organismos[i] = new OrganismoVelocidad();
            }
            else{
                organismos[i] = new OrganismoVision();
            }
        }

        //Inicializando los alimentos
        for(int i = 0; i < 10; i++){
            valorAleatorio = Math.random()*(1-0)+0;
            if(valorAleatorio < 0.3){
                alimentos[i] = new AlimentoEnergia();
            }
            else if(0.3 < valorAleatorio && valorAleatorio < 0.6){
                alimentos[i] = new AlimentoVision();
            }
            else{
                alimentos[i] = new AlimentoVelocidad();
            }
        }

        //Colocando los organismos aleatoriamente
        for(Organismo org: organismos){
            do{
                posX = (int)(Math.floor(Math.random()*49+0));
                posY = (int)(Math.floor(Math.random()*49+0));
                if(matriz[posX][posY].getObjeto() == null){
                    matriz[posX][posY].setObjeto(org);
                    org.setPosition(posX, posY);
                }
                }while(matriz[posX][posY].getObjeto() == null);
        }

        //Colocando los alimentos aleatoriamente
        for(Alimento alimen: alimentos){
            do{
                posX = (int)(Math.floor(Math.random()*49+0));
                posY = (int)(Math.floor(Math.random()*49+0));
                if(matriz[posX][posY].getObjeto() == null){
                    matriz[posX][posY].setObjeto(alimen);
                }
                }while(matriz[posX][posY].getObjeto() == null);
        }
    }

    //Getters
    public Casilla[][] getDimension(){
        return matriz;
    }

    public Organismo[] getOrganismos(){
        return organismos;
    }

    public Casilla getCasilla(int i, int j){
        return matriz[i][j];
    }
}
