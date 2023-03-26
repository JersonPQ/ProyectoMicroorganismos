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

    public void moverse(Organismo o){
        int[] indiceEncontrado = o.pos;
        int[] posicionOrganismo;
        posicionOrganismo = o.getPosicion();
        System.out.println("Organismo velocidad en la posicion (" + posicionOrganismo[0] + ", " + posicionOrganismo[1] + ")");
        if (o instanceof OrganismoVelocidad) {
            indiceEncontrado = busqueda((OrganismoVelocidad) o);
        } else if (o instanceof OrganismoVision) {
            indiceEncontrado = busqueda((OrganismoVision) o);
        }
//        else {
//            busqueda((OrganismoJugador) o);
//        }
        System.out.println(indiceEncontrado[0] + ", " + indiceEncontrado[1]);
        // hacer algoritmo para moverse
    }

    public int[] busqueda(OrganismoVelocidad o){
        int[] indiceObjetoEncontrado = {o.pos[0], o.pos[1]};
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
        while (o.vision > contBusqueda){
            // busqueda horizontal de izquierda a derecha arriba de organismo
            if (buscarFilInicial && filaInicial >= 0){
                for (int i = columnaInicial; i < columnaFinal; i++){
                    if (i >= matriz[0].length){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[filaInicial][i].getObjeto() instanceof Alimento || matriz[filaInicial][i].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = filaInicial;
                        indiceObjetoEncontrado[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if ((matriz[filaInicial][i].getObjeto() instanceof Organismo || matriz[filaInicial][i].getObjeto() instanceof AlimentoVelocidad) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // busqueda vertical de arriba a abajo a la derecha del organismo
            if (buscarColFinal && columnaFinal < matriz[0].length){
                for (int i = filaInicial; i < filaFinal; i++){
                    if (i >= matriz.length){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[i][columnaFinal].getObjeto() instanceof Alimento || matriz[i][columnaFinal].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = i;
                        indiceObjetoEncontrado[1] = columnaFinal;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2));
                        if ((matriz[i][columnaFinal].getObjeto() instanceof Organismo || matriz[i][columnaFinal].getObjeto() instanceof AlimentoVelocidad) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // busqueda horizontal de derecha a izquierda abajo del organismo
            if (buscarFilFinal && filaFinal < matriz.length){
                for (int i = columnaFinal; i > columnaInicial; i--){
                    if (i < 0){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[filaFinal][i].getObjeto() instanceof Alimento || matriz[filaFinal][i].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = filaFinal;
                        indiceObjetoEncontrado[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if ((matriz[filaFinal][i].getObjeto() instanceof Organismo || matriz[filaFinal][i].getObjeto() instanceof AlimentoVelocidad) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // busqueda vertical de abajo hacia arriba a la izquierda del organismo
            if (buscarColInicial && columnaInicial >= 0){
                for (int i = filaFinal; i > filaInicial; i--){
                    if (i < 0){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[i][columnaInicial].getObjeto() instanceof Alimento || matriz[i][columnaInicial].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = i;
                        indiceObjetoEncontrado[1] = columnaInicial;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2));
                        if ((matriz[i][columnaInicial].getObjeto() instanceof Organismo || matriz[i][columnaInicial].getObjeto() instanceof AlimentoVelocidad) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // verificar si el Alimento encontrado es AlimentoVelocidad o si es un Organismo
            if (matriz[indiceObjetoEncontrado[0]][indiceObjetoEncontrado[1]].getObjeto() instanceof AlimentoVelocidad){
                return indiceObjetoEncontrado;
            } else if ((o.pos[0] != indiceObjetoEncontrado[0] && o.pos[1] != indiceObjetoEncontrado[1]) && matriz[indiceObjetoEncontrado[0]][indiceObjetoEncontrado[1]].getObjeto() instanceof Organismo) {
                return indiceObjetoEncontrado;
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
        return indiceObjetoEncontrado;
    }

    public int[] busqueda(OrganismoVision o){
        int[] indiceObjetoEncontrado = {o.pos[0], o.pos[1]};
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
        while (o.vision > contBusqueda){
            // busqueda horizontal de izquierda a derecha arriba de organismo
            if (buscarFilInicial && filaInicial >= 0){
                for (int i = columnaInicial; i < columnaFinal; i++){
                    if (i >= matriz[0].length){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[filaInicial][i].getObjeto() instanceof Alimento || matriz[filaInicial][i].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = filaInicial;
                        indiceObjetoEncontrado[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if ((matriz[filaInicial][i].getObjeto() instanceof Organismo || matriz[filaInicial][i].getObjeto() instanceof AlimentoVision) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // busqueda vertical de arriba a abajo a la derecha del organismo
            if (buscarColFinal && columnaFinal < matriz[0].length){
                for (int i = filaInicial; i < filaFinal; i++){
                    if (i >= matriz.length){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[i][columnaFinal].getObjeto() instanceof Alimento || matriz[i][columnaFinal].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = i;
                        indiceObjetoEncontrado[1] = columnaFinal;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2));
                        if ((matriz[i][columnaFinal].getObjeto() instanceof Organismo || matriz[i][columnaFinal].getObjeto() instanceof AlimentoVision) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // busqueda horizontal de derecha a izquierda abajo del organismo
            if (buscarFilFinal && filaFinal < matriz.length){
                for (int i = columnaFinal; i > columnaInicial; i--){
                    if (i < 0){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[filaFinal][i].getObjeto() instanceof Alimento || matriz[filaFinal][i].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = filaFinal;
                        indiceObjetoEncontrado[1] = i;
                        distanciaPuntos = Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if ((matriz[filaFinal][i].getObjeto() instanceof Organismo || matriz[filaFinal][i].getObjeto() instanceof AlimentoVision) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // busqueda vertical de abajo hacia arriba a la izquierda del organismo
            if (buscarColInicial && columnaInicial >= 0){
                for (int i = filaFinal; i > filaInicial; i--){
                    if (i < 0){
                        break;
                    }

                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if ((matriz[i][columnaInicial].getObjeto() instanceof Alimento || matriz[i][columnaInicial].getObjeto() instanceof Organismo) && distanciaPuntos > Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2))){
                        indiceObjetoEncontrado[0] = i;
                        indiceObjetoEncontrado[1] = columnaInicial;
                        distanciaPuntos = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2));
                        if ((matriz[i][columnaInicial].getObjeto() instanceof Organismo || matriz[i][columnaInicial].getObjeto() instanceof AlimentoVision) && distanciaPuntos == contBusqueda + 1){
                            return indiceObjetoEncontrado;
                        }
                    }
                }
            }

            // verificar si el Alimento encontrado es AlimentoVision o si es un Organismo
            if (matriz[indiceObjetoEncontrado[0]][indiceObjetoEncontrado[1]].getObjeto() instanceof AlimentoVision){
                return indiceObjetoEncontrado;
            } else if ((indiceObjetoEncontrado[0] != o.pos[0] && indiceObjetoEncontrado[1] != o.pos[1]) && matriz[indiceObjetoEncontrado[0]][indiceObjetoEncontrado[1]].getObjeto() instanceof Organismo) {
                return indiceObjetoEncontrado;
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

        // si no encuentra Alimento u Organismo en el radio de vision retorna la misma posicion del Organismo
        return indiceObjetoEncontrado;
    }

    // metodo busqueda de Jugador solo habilita los botones alrededor
//    public void habilitarBotones(OrganismoJugador o){
//        filaInicial = o.pos[0] - 1;
//        filaFinal = o.pos[0] + 1;
//        columnaInicial = o.pos[1] - 1;
//        columnaFinal = o.pos[1] + 1;
//        // habilita boton de arriba
//        if (filaInicial >= 0)
//            matriz[filaInicial][o.pos[1]].boton.setEnabled(true);
//
//        // habilita boton de la derecha
//        if (columnaFinal < matriz[0].length)
//            matriz[o.pos[0]][columnaFinal].boton.setEnabled(true);
//
//        // habilita boton de abajo
//        if (filaFinal < matriz.length)
//            matriz[filaFinal][o.pos[1]].boton.setEnabled(true);
//
//        // habilita boton de la izquierda
//        if (columnaInicial >= 0)
//            matriz[o.pos[0]][columnaInicial].boton.setEnabled(true);
//    }

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
