public class Mapa {
    private Organismo[] organismos;
    private Alimento[] alimentos;
    private Casilla[][] matriz;
    private Casilla casilla;
    private double valorAleatorio;
    private int posX;
    private int posY;

    private int filaInicial, filaFinal, columnaInicial, columnaFinal, contBusqueda;
    private double distanciaPuntos;
    private boolean buscarFilInicial = true, buscarFilFinal = true;
    private boolean buscarColInicial = true, buscarColFinal = true;

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

        System.out.println(organismos[0].getPosicion()[0] + " - " + organismos[0].getPosicion()[1]);
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

    public int[] busqueda(OrganismoVelocidad o){
        int[] indiceAlimento = {o.pos[0], o.pos[1]};
        // distancia maxima que puede haber entre puntos
        distanciaPuntos = Math.sqrt(Math.pow(matriz.length, 2) + Math.pow(matriz[0].length, 2));
        contBusqueda = 0;
        filaInicial = o.pos[0] - 1;
        filaFinal = o.pos[0] + 1;
        columnaInicial = o.pos[1] - 1;
        columnaFinal = o.pos[1] + 1;

        // flags indican si es necesario buscar en esas filas/columnas
        if (filaInicial < 0) {
            filaInicial = 0;
            buscarFilInicial = false;
        }
        
        if (filaFinal >= matriz.length){
            filaFinal = matriz.length -1;
            buscarFilFinal = false;
        }
        
        if (columnaInicial < 0){
            columnaInicial = 0;
            buscarColInicial = false;
        }
        
        if (columnaFinal >= matriz[0].length){
            columnaFinal = matriz[0].length - 1;
            buscarColFinal = false;
        }
        // falta verificar si Organismo tiene un organismo cerca
        while (o.vision > contBusqueda){
            // busqueda horizontal de izquierda a derecha arriba de organismo
            if (buscarFilInicial && filaInicial >= 0){
                for (int i = columnaInicial; i < columnaFinal; i++){
                    if (i >= matriz[0].length){
                        break;
                    }
                    
                    if ((matriz[filaInicial][i].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceAlimento[0] = filaInicial;
                        indiceAlimento[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                    }
                }
            }

            // busqueda vertical de arriba a abajo a la derecha del organismo
            if (buscarColFinal && columnaFinal < matriz[0].length){
                for (int i = filaInicial; i < filaFinal; i++){
                    if (i >= matriz.length){
                        break;
                    }
                    
                    if ((matriz[i][columnaFinal].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2))){
                        indiceAlimento[0] = i;
                        indiceAlimento[1] = columnaFinal;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2));
                    }
                }
            }

            // busqueda horizontal de derecha a izquierda abajo del organismo
            if (buscarFilFinal && filaFinal < matriz.length){
                for (int i = columnaFinal; i > columnaInicial; i--){
                    if (i < 0){
                        break;
                    }
                    
                    if ((matriz[filaFinal][i].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceAlimento[0] = filaFinal;
                        indiceAlimento[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                    }
                }
            }

            // busqueda vertical de abajo hacia arriba a la izquierda del organismo
            if (buscarColInicial && columnaInicial >= 0){
                for (int i = filaFinal; i > filaInicial; i--){
                    if (i < 0){
                        break;
                    }
                    
                    if ((matriz[i][columnaInicial].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2))){
                        indiceAlimento[0] = i;
                        indiceAlimento[1] = columnaInicial;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2));
                    }
                }
            }

            // verificar si el Alimento encontrado es AlimentoVelocidad
            if (matriz[indiceAlimento[0]][indiceAlimento[1]].getObjeto() instanceof AlimentoVelocidad){
                return indiceAlimento;
            }

            if (filaInicial > 0){
                filaInicial--;
            }
            
            if (filaFinal < matriz.length - 1){
                filaFinal++;
            }
            
            if (columnaInicial > 0){
                columnaInicial--;
            }

            if (columnaFinal < matriz[0].length - 1){
                columnaFinal++;
            }

            ++contBusqueda;
        }

        // si no encuentra Alimento retorna la mismas posicion del Organismo
        return indiceAlimento;
    }

    public int[] busqueda(OrganismoVision o){
        int[] indiceAlimento = {o.pos[0], o.pos[1]};
        // distancia maxima que puede haber entre puntos
        distanciaPuntos = Math.sqrt(Math.pow(matriz.length, 2) + Math.pow(matriz[0].length, 2));
        contBusqueda = 0;
        filaInicial = o.pos[0] - 1;
        filaFinal = o.pos[0] + 1;
        columnaInicial = o.pos[1] - 1;
        columnaFinal = o.pos[1] + 1;

        // flags indican si es necesario buscar en esas filas/columnas
        if (filaInicial < 0) {
            filaInicial = 0;
            buscarFilInicial = false;
        }
        
        if (filaFinal >= matriz.length){
            filaFinal = matriz.length -1;
            buscarFilFinal = false;
        }
        
        if (columnaInicial < 0){
            columnaInicial = 0;
            buscarColInicial = false;
        }
        
        if (columnaFinal >= matriz[0].length){
            columnaFinal = matriz[0].length - 1;
            buscarColFinal = false;
        }
        // falta verificar si Organismo tiene un organismo cerca
        while (o.vision > contBusqueda){
            // busqueda horizontal de izquierda a derecha arriba de organismo
            if (buscarFilInicial && filaInicial >= 0){
                for (int i = columnaInicial; i < columnaFinal; i++){
                    if (i >= matriz[0].length){
                        break;
                    }
                    
                    if ((matriz[filaInicial][i].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceAlimento[0] = filaInicial;
                        indiceAlimento[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                    }
                }
            }

            // busqueda vertical de arriba a abajo a la derecha del organismo
            if (buscarColFinal && columnaFinal < matriz[0].length){
                for (int i = filaInicial; i < filaFinal; i++){
                    if (i >= matriz.length){
                        break;
                    }
                    
                    if ((matriz[i][columnaFinal].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2))){
                        indiceAlimento[0] = i;
                        indiceAlimento[1] = columnaFinal;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2));
                    }
                }
            }

            // busqueda horizontal de derecha a izquierda abajo del organismo
            if (buscarFilFinal && filaFinal < matriz.length){
                for (int i = columnaFinal; i > columnaInicial; i--){
                    if (i < 0){
                        break;
                    }
                    
                    if ((matriz[filaFinal][i].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceAlimento[0] = filaFinal;
                        indiceAlimento[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                    }
                }
            }

            // busqueda vertical de abajo hacia arriba a la izquierda del organismo
            if (buscarColInicial && columnaInicial >= 0){
                for (int i = filaFinal; i > filaInicial; i--){
                    if (i < 0){
                        break;
                    }
                    
                    if ((matriz[i][columnaInicial].getObjeto()) instanceof Alimento && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2))){
                        indiceAlimento[0] = i;
                        indiceAlimento[1] = columnaInicial;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2));
                    }
                }
            }

            // verificar si el Alimento encontrado es AlimentoVelocidad
            if (matriz[indiceAlimento[0]][indiceAlimento[1]].getObjeto() instanceof AlimentoVision){
                return indiceAlimento;
            }

            if (filaInicial > 0){
                filaInicial--;
            }
            
            if (filaFinal < matriz.length - 1){
                filaFinal++;
            }
            
            if (columnaInicial > 0){
                columnaInicial--;
            }

            if (columnaFinal < matriz[0].length - 1){
                columnaFinal++;
            }

            ++contBusqueda;
        }

        // si no encuentra Alimento retorna la mismas posicion del Organismo
        return indiceAlimento;
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
