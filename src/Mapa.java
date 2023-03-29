import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

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
    private Random rnd;
    private int contadorOrganismos;

    public Mapa(){
        matriz = new Casilla[50][50];
        alimentos = new Alimento[20];
        organismos = new Organismo[10];
        contadorOrganismos = 1;

        //Inicializando cada Casilla en la matriz
        for (int i = 0; i < 50; i++){
            for(int j = 0; j < 50; j++){
                casilla = new Casilla();
                matriz[i][j] = casilla;
            }
        }

        // incializar y colocar organismoJugador
        organismos[0] = new OrganismoJugador();
        while (true) {
            posX = (int)(Math.floor(Math.random()*49+0));
            posY = (int)(Math.floor(Math.random()*49+0));
            if(matriz[posX][posY].getObjeto() == null){
                matriz[posX][posY].setObjeto(organismos[0]);
                organismos[0].setPosition(posX, posY);
                break;
            }
        }


        //Inicializando los organismos y colocandolos aleatoriamente
        while (contadorOrganismos < organismos.length){
            valorAleatorio = Math.random()*(1-0)+0;
            if(valorAleatorio < 0.5){
                organismos[contadorOrganismos] = new OrganismoVelocidad();
            }
            else{
                organismos[contadorOrganismos] = new OrganismoVision();
            }

            while (true){
                posX = (int)(Math.floor(Math.random()*49+0));
                posY = (int)(Math.floor(Math.random()*49+0));
                if(matriz[posX][posY].getObjeto() == null){
                    matriz[posX][posY].setObjeto(organismos[contadorOrganismos]);
                    organismos[contadorOrganismos].setPosition(posX, posY);
                    break;
                }
            }

            ++contadorOrganismos;
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
                posX = (int)(Math.floor(Math.random()*49+0));
                posY = (int)(Math.floor(Math.random()*49+0));
                if(matriz[posX][posY].getObjeto() == null){
                    matriz[posX][posY].setObjeto(alimentos[i]);
                    //Aqui vamos a poner la imagen del Alimento
                    break;
                }
            }
        }

        System.out.println(organismos[0].getPosicion()[0] + " - " + organismos[0].getPosicion()[1]);
    }

    public void crearOrganismo(){
        valorAleatorio = Math.random()*(1-0)+0;
        if(valorAleatorio < 0.5){
            organismos[contadorOrganismos] = new OrganismoVelocidad();
        }
        else{
            organismos[contadorOrganismos] = new OrganismoVision();
        }

        while (true){
            posX = (int)(Math.floor(Math.random()*49+0));
            posY = (int)(Math.floor(Math.random()*49+0));
            if(matriz[posX][posY].getObjeto() == null){
                matriz[posX][posY].setObjeto(organismos[contadorOrganismos]);
                organismos[contadorOrganismos].setPosition(posX, posY);
                ImageIcon imagen = ((NPC) matriz[posX][posY].getObjeto()).setImagen();
                (matriz[posX][posY].boton).setIcon(new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                break;
            }
        }

        ++contadorOrganismos;
    }

    public void eliminarOrganismo(Organismo organismoEliminar){
        int indiceOrganismo = Arrays.asList(organismos).indexOf(organismoEliminar);
        for (int i = indiceOrganismo; i < organismos.length - 1; i++) {
            organismos[i] = organismos[i + 1];
        }

        --contadorOrganismos;
        crearOrganismo();
    }

    public void crearAlimento(int indiceAlimento){
        valorAleatorio = Math.random()*(1-0)+0;
        if(valorAleatorio < 0.3){
            alimentos[indiceAlimento] = new AlimentoEnergia();
        }
        else if(0.3 < valorAleatorio && valorAleatorio < 0.6){
            alimentos[indiceAlimento] = new AlimentoVision();
        }
        else{
            alimentos[indiceAlimento] = new AlimentoVelocidad();
        }

        while (true){
            posX = (int)(Math.floor(Math.random()*49+0));
            posY = (int)(Math.floor(Math.random()*49+0));
            if(matriz[posX][posY].getObjeto() == null){
                matriz[posX][posY].setObjeto(alimentos[indiceAlimento]);
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
        int[] indiceEncontrado = organismo.pos;
        int[] posicionOrganismo = organismo.getPosicion();
        int siguiCasillaX = posicionOrganismo[0];
        int siguiCasillaY = posicionOrganismo[1];
        double distanciaMoverVert, distanciaMoverHoriz;
        boolean moverAleatorio, sumarORestarIndice;
        boolean huir = false;
        int[] siguienteMovimiento = new int[2];
        rnd = new Random();
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
                huir = true;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[0] > indiceEncontrado[0]){
                ++siguiCasillaX;
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
                huir = true;
            } else if (casillaObjetoEncontrado.getObjeto() instanceof Organismo && !(organismo.comprobarAtaque((Organismo) casillaObjetoEncontrado.getObjeto())) && posicionOrganismo[1] > indiceEncontrado[1]){
                ++siguiCasillaY;
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
        // llama funcion atacar() en caso de que la sigiuenteCasilla no sea null
        if (siguienteCasilla.getObjeto() != null){
            if (siguienteCasilla.getObjeto() instanceof Organismo){
                Organismo organismoAAtacar = (Organismo) siguienteCasilla.getObjeto();
                if (organismo.atacar(organismoAAtacar)){
                    eliminarOrganismo(organismoAAtacar);
                }

            } else if (siguienteCasilla.getObjeto() instanceof AlimentoEnergia) {
                AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguienteCasilla.getObjeto();
                organismo.atacar(alimentoAComer);
                eliminarAlimento(alimentoAComer);
            } else if (siguienteCasilla.getObjeto() instanceof AlimentoVelocidad) {
                AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguienteCasilla.getObjeto();
                organismo.atacar(alimentoAComer);
                eliminarAlimento(alimentoAComer);
            } else if (siguienteCasilla.getObjeto() instanceof AlimentoVision) {
                AlimentoVision alimentoAComer = (AlimentoVision) siguienteCasilla.getObjeto();
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
    }

    public int[] busqueda(OrganismoVelocidad o){
        boolean buscarFilInicial = true, buscarFilFinal = true;
        boolean buscarColInicial = true, buscarColFinal = true;
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
        boolean buscarFilInicial = true, buscarFilFinal = true;
        boolean buscarColInicial = true, buscarColFinal = true;
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
