import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Mapa{
    private Organismo[] organismos;
    private Alimento[] alimentos;
    private Casilla[][] matriz;
    private double valorAleatorio;
    private int posX;
    private int posY;
    private boolean jugando;

    public Mapa(int cantOrganismos, int cantAlimentos, int tamanoMatriz){
        matriz = new Casilla[tamanoMatriz][tamanoMatriz];
        alimentos = new Alimento[cantAlimentos];
        organismos = new Organismo[cantOrganismos];
        jugando = true;

        //Inicializando cada Casilla en la matriz
        for (int i = 0; i < tamanoMatriz; i++){
            for(int j = 0; j < tamanoMatriz; j++){
                Casilla casilla = new Casilla();
                matriz[i][j] = casilla;
            }
        }

        // incializar y colocar organismoJugador
        organismos[0] = new OrganismoJugador();
        while (true) {
            posX = (int)(Math.floor(Math.random()* (tamanoMatriz - 1)));
            posY = (int)(Math.floor(Math.random()* (tamanoMatriz - 1)));
            if(matriz[posX][posY].getObjeto() == null){
                matriz[posX][posY].setObjeto(organismos[0]);
                organismos[0].setPosition(posX, posY);
                break;
            }
        }


        //Inicializando los organismos y colocandolos aleatoriamente
        for (int i = 1; i < organismos.length; i++) {
            valorAleatorio = Math.random()*(1-0)+0;
            if(valorAleatorio < 0.5){
                organismos[i] = new OrganismoVelocidad();
            }
            else{
                organismos[i] = new OrganismoVision();
            }

            while (true){
                posX = (int)(Math.floor(Math.random()* (tamanoMatriz - 1)));
                posY = (int)(Math.floor(Math.random()* (tamanoMatriz - 1)));
                if(matriz[posX][posY].getObjeto() == null){
                    matriz[posX][posY].setObjeto(organismos[i]);
                    organismos[i].setPosition(posX, posY);
                    break;
                }
            }
        }

        //Inicializando los alimentos y colocandolos aleatoriamente
        for (int i = 0; i < alimentos.length; i++) {
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

            while (true){
                posX = (int)(Math.floor(Math.random()* (tamanoMatriz - 1)));
                posY = (int)(Math.floor(Math.random()* (tamanoMatriz - 1)));
                if(matriz[posX][posY].getObjeto() == null){
                    matriz[posX][posY].setObjeto(alimentos[i]);
                    alimentos[i].setPosition(posX, posY);
                    break;
                }
            }
        }

        System.out.println(organismos[0].getPosition()[0] + " - " + organismos[0].getPosition()[1]);
    }

    public void crearOrganismo(int _indiceOrganismo){;
        valorAleatorio = Math.random()*(1-0)+0;
        if(valorAleatorio < 0.5){
            organismos[_indiceOrganismo] = new OrganismoVelocidad();
        }
        else{
            organismos[_indiceOrganismo] = new OrganismoVision();
        }

        while (true){
            posX = (int)(Math.floor(Math.random()* (matriz.length - 1)));
            posY = (int)(Math.floor(Math.random()* (matriz.length - 1)));
            if(matriz[posX][posY].getObjeto() == null){
                matriz[posX][posY].setObjeto(organismos[_indiceOrganismo]);
                organismos[_indiceOrganismo].setPosition(posX, posY);
                ImageIcon imagen = ((NPC) matriz[posX][posY].getObjeto()).setImagen();
                (matriz[posX][posY].boton).setIcon(new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                break;
            }
        }
    }

    public void eliminarOrganismo(Organismo organismoEliminar){
        int indiceOrganismo = Arrays.asList(organismos).indexOf(organismoEliminar);
        System.out.println("ELIMINA ORGANISMO EN POSICION (" + indiceOrganismo + ")");
        crearOrganismo(indiceOrganismo);
    }

    public void crearAlimento(int _indiceAlimento){
        valorAleatorio = Math.random()*(1-0)+0;
        if(valorAleatorio < 0.3){
            alimentos[_indiceAlimento] = new AlimentoEnergia();
        }
        else if(0.3 < valorAleatorio && valorAleatorio < 0.6){
            alimentos[_indiceAlimento] = new AlimentoVision();
        }
        else{
            alimentos[_indiceAlimento] = new AlimentoVelocidad();
        }

        while (true){
            posX = (int)(Math.floor(Math.random()* (matriz.length - 1)));
            posY = (int)(Math.floor(Math.random()* (matriz.length - 1)));
            if(matriz[posX][posY].getObjeto() == null){
                matriz[posX][posY].setObjeto(alimentos[_indiceAlimento]);
                alimentos[_indiceAlimento].setPosition(posX, posY);
                ImageIcon imagen = ((NPC) matriz[posX][posY].getObjeto()).setImagen();
                (matriz[posX][posY].boton).setIcon(new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                break;
            }
        }
    }

    public void eliminarAlimento(Alimento alimentoEliminar){
        int indiceAlimento = Arrays.asList(alimentos).indexOf(alimentoEliminar);
        crearAlimento(indiceAlimento);
    }

    public void moverse(Organismo organismo){
        Casilla casillaObjetoEncontrado, casillaJugador, siguienteCasilla;
        ImageIcon imagenJugador = organismo.setImagen();
        Object objetoSiguiCasilla;

        // obtiene array de la posicion del organismo
        int[] posicionOrganismo = organismo.getPosition();

        // indice de objeto encontrado en la busqueda luego
        int[] indiceEncontrado = organismo.pos;

        // indices de siguientes casillas incializadas en la misma posicion del organismo
        int siguiCasillaX = posicionOrganismo[0];
        int siguiCasillaY = posicionOrganismo[1];

        // variables para formula de ditancia entre puntos
        double distanciaMoverVert, distanciaMoverHoriz;

        // variables para determinar movimiento aleatorio de NPC
        boolean moverAleatorio, sumarORestarIndice;

        // determinar si NPC huye o persigue
        boolean huir = false;

        // array para los indices del siguiente movimiento
        int[] siguienteMovimiento = new int[2];
        Random rnd = new Random();
        if (organismo instanceof OrganismoVelocidad) {
            System.out.println("Organismo velocidad en la posicion (" + posicionOrganismo[0] + ", " + posicionOrganismo[1] + ")");
            indiceEncontrado = busqueda((OrganismoVelocidad) organismo);
        } else if (organismo instanceof OrganismoVision) {
            System.out.println("Organismo vision en la posicion (" + posicionOrganismo[0] + ", " + posicionOrganismo[1] + ")");
            indiceEncontrado = busqueda((OrganismoVision) organismo);
        }
//        else {
//            busqueda((OrganismoJugador) o);
//        }
        System.out.println(indiceEncontrado[0] + ", " + indiceEncontrado[1]);
        // casilla donde esta el objeto encontrado
        casillaObjetoEncontrado = matriz[indiceEncontrado[0]][indiceEncontrado[1]];
        // casilla donde esta el organismo
        casillaJugador = matriz[posicionOrganismo[0]][posicionOrganismo[1]];
        // si el indice encontrado es igual al del organismo este se mueve aleatoriamente
        if (posicionOrganismo[0] == indiceEncontrado[0] && posicionOrganismo[1] == indiceEncontrado[1]){
            while (true){
                // si moverAleatorio es true mueve horizontalmente
                moverAleatorio = rnd.nextBoolean();
                // si sumarORestarIndice es true le suma uno al indice correspondiente
                sumarORestarIndice = rnd.nextBoolean();
                if (moverAleatorio && sumarORestarIndice){
                    siguienteMovimiento[0] = posicionOrganismo[0];
                    siguienteMovimiento[1] = posicionOrganismo[1] + 1;
                } else if (moverAleatorio && !sumarORestarIndice) {
                    siguienteMovimiento[0] = posicionOrganismo[0];
                    siguienteMovimiento[1] = posicionOrganismo[1] - 1;
                } else if (!moverAleatorio && sumarORestarIndice) {
                    siguienteMovimiento[0] = posicionOrganismo[0] + 1;
                    siguienteMovimiento[1] = posicionOrganismo[1];
                } else {
                    siguienteMovimiento[0] = posicionOrganismo[0] - 1;
                    siguienteMovimiento[1] = posicionOrganismo[1];
                }

                // evalua si indices estan dentro de la matriz
                if (0 <= siguienteMovimiento[0] && siguienteMovimiento[0] < matriz.length && 0 <= siguienteMovimiento[1] && siguienteMovimiento[1] < matriz[0].length){
                    break;
                }
            }
        } else {
            // Comparar si los indices de indiceEncontrado es mayor o menor al de los indices del Organismo, ademas de que el objeto
            // de el indice encontrado sea Alimento o un Organismo que pueda comer

            // evalucacion indices X
            if ((casillaObjetoEncontrado.getObjeto() instanceof Alimento || (casillaObjetoEncontrado.getObjeto() instanceof Organismo && organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto()))) && posicionOrganismo[0] < indiceEncontrado[0]){
                ++siguiCasillaX;
                huir = false;
            } else if ((casillaObjetoEncontrado.getObjeto() instanceof Alimento || (casillaObjetoEncontrado.getObjeto() instanceof Organismo && organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto()))) && posicionOrganismo[0] > indiceEncontrado[0]) {
                --siguiCasillaX;
                huir = false;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[0] < indiceEncontrado[0]){
                --siguiCasillaX;
                if (siguiCasillaX < 0){
                    // le suma 2 para que en lugar de irse para la arriba y se salga de la matriz, toma como posible
                    // movimiento hacia abajo
                    siguiCasillaX += 2;
                }

                huir = true;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[0] > indiceEncontrado[0]){
                ++siguiCasillaX;
                if (siguiCasillaX > matriz.length - 1){
                    // le resta 2 para que en lugar de irse para abajo y se salga de la matriz, toma como posible
                    // movimiento hacia arriba
                    siguiCasillaX -= 2;
                }

                huir = true;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[0] == indiceEncontrado[0]) {
                // toma decision de movimiento con base si esta en la primera mitad de filas de la matriz o en la segunda
                if (posicionOrganismo[0] > ((matriz.length) - 1) / 2){
                    --siguiCasillaX;
                } else {
                    ++siguiCasillaX;
                }

                huir = true;
            }

            // evaluacion indices Y
            if ((casillaObjetoEncontrado.getObjeto() instanceof Alimento || (casillaObjetoEncontrado.getObjeto() instanceof Organismo && organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto()))) && posicionOrganismo[1] < indiceEncontrado[1]){
                ++siguiCasillaY;
                huir = false;
            } else if ((casillaObjetoEncontrado.getObjeto() instanceof Alimento || (casillaObjetoEncontrado.getObjeto() instanceof Organismo && organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto()))) && posicionOrganismo[1] > indiceEncontrado[1]) {
                --siguiCasillaY;
                huir = false;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[1] < indiceEncontrado[1]){
                --siguiCasillaY;
                if (siguiCasillaY < 0){
                    // le suma 2 para que en lugar de irse para la izquierda y se salga de la matriz, toma como posible
                    // movimiento a la derecha
                    siguiCasillaY += 2;
                }

                huir = true;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[1] > indiceEncontrado[1]){
                ++siguiCasillaY;
                if (siguiCasillaY > matriz.length - 1){
                    // le resta 2 para que en lugar de irse para la derecha y se salga de la matriz, toma como posible
                    // movimiento a la izquierda
                    siguiCasillaY -= 2;
                }

                huir = true;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[1] == indiceEncontrado[1]) {
                // toma decision de movimiento con base si esta en la primera mitad de columnas de la matriz o en la segunda
                if (posicionOrganismo[1] > ((matriz[0].length) - 1) / 2){
                    --siguiCasillaY;
                } else {
                    ++siguiCasillaY;
                }

                huir = true;
            }

            distanciaMoverVert = Math.sqrt(Math.pow(indiceEncontrado[0] - siguiCasillaX, 2) + Math.pow(indiceEncontrado[1] - posicionOrganismo[1], 2));
            distanciaMoverHoriz = Math.sqrt(Math.pow(indiceEncontrado[0] - posicionOrganismo[0], 2) + Math.pow(indiceEncontrado[1] - siguiCasillaY, 2));
            // determina los siguientes movimientos con base en la distancia entre los movimientos
            if (distanciaMoverHoriz == distanciaMoverVert){
                moverAleatorio = rnd.nextBoolean();
                // si es true se mueve horizontalmente, caso contrario verticalmente
                if (moverAleatorio){
                    siguienteMovimiento[0] = posicionOrganismo[0];
                    siguienteMovimiento[1] = siguiCasillaY;
                } else {
                    siguienteMovimiento[0] = siguiCasillaX;
                    siguienteMovimiento[1] = posicionOrganismo[1];
                }
            } else if (!huir && distanciaMoverHoriz > distanciaMoverVert) {
                siguienteMovimiento[0] = siguiCasillaX;
                siguienteMovimiento[1] = posicionOrganismo[1];
            } else if (huir && distanciaMoverHoriz < distanciaMoverVert) {
                siguienteMovimiento[0] = siguiCasillaX;
                siguienteMovimiento[1] = posicionOrganismo[1];
            } else if (huir && distanciaMoverHoriz > distanciaMoverVert) {
                siguienteMovimiento[0] = posicionOrganismo[0];
                siguienteMovimiento[1] = siguiCasillaY;
            } else {
                siguienteMovimiento[0] = posicionOrganismo[0];
                siguienteMovimiento[1] = siguiCasillaY;
            }
        }

        //
        siguienteCasilla = matriz[siguienteMovimiento[0]][siguienteMovimiento[1]];
        objetoSiguiCasilla = siguienteCasilla.getObjeto(); // toma objeto de la siguiente casilla a moverse
        // llama funcion atacar() en caso de que la sigiuenteCasilla no sea null
        if (objetoSiguiCasilla != null){
            if (objetoSiguiCasilla instanceof OrganismoJugador){
                // le asigna jugando false porque se comio al organismoJugador
//                ((OrganismoJugador) objetoSiguiCasilla).setJugadorJugando(false);
                jugando = false;
            } else if (objetoSiguiCasilla instanceof Organismo){
                Organismo organismoAAtacar = (Organismo) objetoSiguiCasilla;
                if (organismo.atacar(organismoAAtacar)){
                    eliminarOrganismo(organismoAAtacar);
                }

            } else if (objetoSiguiCasilla instanceof AlimentoEnergia) {
                AlimentoEnergia alimentoAComer = (AlimentoEnergia) objetoSiguiCasilla;
                organismo.atacar(alimentoAComer);
                eliminarAlimento(alimentoAComer);
            } else if (objetoSiguiCasilla instanceof AlimentoVelocidad) {
                AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) objetoSiguiCasilla;
                organismo.atacar(alimentoAComer);
                eliminarAlimento(alimentoAComer);
            } else if (objetoSiguiCasilla instanceof AlimentoVision) {
                AlimentoVision alimentoAComer = (AlimentoVision) objetoSiguiCasilla;
                organismo.atacar(alimentoAComer);
                eliminarAlimento(alimentoAComer);
            }
        }

        casillaJugador.boton.setIcon(null); //Le quitamos la imagen
        casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

        //Colocar el objeto en las otra casilla y la imagen
        siguienteCasilla.setObjeto(organismo);
        siguienteCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        organismo.setPosition(siguienteMovimiento[0], siguienteMovimiento[1]);
        System.out.println("Siguiente casilla es (" + siguienteMovimiento[0] + ", " + siguienteMovimiento[1] + ")\n");
    }

    public int[] busqueda(OrganismoVelocidad o){
        boolean buscarFilInicial = true, buscarFilFinal = true;
        boolean buscarColInicial = true, buscarColFinal = true;
        int[] indiceObjetoEncontrado = {o.pos[0], o.pos[1]};
        Object objetoEncontrado = matriz[indiceObjetoEncontrado[0]][indiceObjetoEncontrado[1]];

        // variable toma valor del objeto que esta en cada casilla al evaluar
        Object objetoEvaluando;

        boolean encontradoEsOrganismo, encontradoEsAliEnergia, encontradoEsAliVel, encontradoEsAliVis;

        // distancia maxima que puede haber entre puntos
        double distanciaPuntos = Math.sqrt(Math.pow(matriz.length, 2) + Math.pow(matriz[0].length, 2));

        double distanciaObjetoEvaluando;

        // contador de busqueda que determina en que momento detenerse con base a la vision del organismo
        int contBusqueda = 0;

        // fila donde inciara y terminara busqueda
        int filaInicial = o.pos[0] - 1;
        int filaFinal = o.pos[0] + 1;

        // columna donde iniciara y terminara busqueda
        int columnaInicial = o.pos[1] - 1;
        int columnaFinal = o.pos[1] + 1;

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

                    objetoEvaluando = matriz[filaInicial][i].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null){
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)){
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))){
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel))){
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel && !encontradoEsAliVis))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
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

                    objetoEvaluando = matriz[i][columnaFinal].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null) {
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)) {
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))) {
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel))) {
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel && !encontradoEsAliVis))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
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

                    objetoEvaluando = matriz[filaFinal][i].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null) {
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)) {
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))) {
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel))) {
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel && !encontradoEsAliVis))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
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

                    objetoEvaluando = matriz[i][columnaInicial].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null) {
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)) {
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))) {
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel))) {
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVel && !encontradoEsAliVis))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        }
                    }
                }
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

        // si no encuentra Alimento retorna la misma posicion del Organismo
        return indiceObjetoEncontrado;
    }

    public int[] busqueda(OrganismoVision o){
        boolean buscarFilInicial = true, buscarFilFinal = true;
        boolean buscarColInicial = true, buscarColFinal = true;
        int[] indiceObjetoEncontrado = {o.pos[0], o.pos[1]};
        Object objetoEncontrado = matriz[indiceObjetoEncontrado[0]][indiceObjetoEncontrado[1]];

        // variable toma valor del objeto que esta en cada casilla al evaluar
        Object objetoEvaluando;

        boolean encontradoEsOrganismo, encontradoEsAliEnergia, encontradoEsAliVel, encontradoEsAliVis;

        // distancia maxima que puede haber entre puntos
        double distanciaPuntos = Math.sqrt(Math.pow(matriz.length, 2) + Math.pow(matriz[0].length, 2));

        double distanciaObjetoEvaluando;

        // contador de busqueda que determina en que momento detenerse con base a la vision del organismo
        int contBusqueda = 0;

        // fila donde inciara y terminara busqueda
        int filaInicial = o.pos[0] - 1;
        int filaFinal = o.pos[0] + 1;

        // columna donde iniciara y terminara busqueda
        int columnaInicial = o.pos[1] - 1;
        int columnaFinal = o.pos[1] + 1;

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

                    objetoEvaluando = matriz[filaInicial][i].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null) {
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(filaInicial - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)){
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))){
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis))){
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis && !encontradoEsAliVel))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaInicial;
                            indiceObjetoEncontrado[1] = i;
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

                    objetoEvaluando = matriz[i][columnaFinal].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null) {
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaFinal - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)) {
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))) {
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis))) {
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis && !encontradoEsAliVel))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaFinal;
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

                    objetoEvaluando = matriz[filaFinal][i].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null) {
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(filaFinal - o.pos[0], 2) + Math.pow(i - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)) {
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))) {
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis))) {
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis && !encontradoEsAliVel))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = filaFinal;
                            indiceObjetoEncontrado[1] = i;
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

                    objetoEvaluando = matriz[i][columnaInicial].getObjeto();
                    // evalua si la posicion a evaluar es un Alimento o un Organismo y que la distancia entre el Organismo que realiza la busqueda
                    // y el Alimento u Organismo encontrado es menor
                    if (objetoEvaluando != null) {
                        encontradoEsOrganismo = objetoEncontrado instanceof Organismo;
                        encontradoEsAliEnergia = objetoEncontrado instanceof AlimentoEnergia;
                        encontradoEsAliVis = objetoEncontrado instanceof AlimentoVision;
                        encontradoEsAliVel = objetoEncontrado instanceof AlimentoVelocidad;
                        distanciaObjetoEvaluando = Math.sqrt(Math.pow(i - o.pos[0], 2) + Math.pow(columnaInicial - o.pos[1], 2));
                        if (objetoEvaluando instanceof Organismo && ((encontradoEsOrganismo && distanciaPuntos > distanciaObjetoEvaluando) || !encontradoEsOrganismo)) {
                            // toma valores para objeto organismo
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        } else if (objetoEvaluando instanceof AlimentoEnergia && ((encontradoEsAliEnergia && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia))) {
                            // toma valores para alimento energia
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        } else if (objetoEvaluando instanceof AlimentoVision && ((encontradoEsAliVis && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis))) {
                            // toma valores para alimento de velocidad
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        } else if (objetoEvaluando instanceof AlimentoVelocidad && ((encontradoEsAliVel && distanciaPuntos > distanciaObjetoEvaluando) || (!encontradoEsOrganismo && !encontradoEsAliEnergia && !encontradoEsAliVis && !encontradoEsAliVel))){
                            // toma valores para alimento vision
                            distanciaPuntos = distanciaObjetoEvaluando;
                            objetoEncontrado = objetoEvaluando;
                            indiceObjetoEncontrado[0] = i;
                            indiceObjetoEncontrado[1] = columnaInicial;
                        }
                    }
                }
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

    public boolean getJugando(){
        return jugando;
    }

    // setters
    public void setJugando(boolean _jugando){
        this.jugando = _jugando;
    }
}
