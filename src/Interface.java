import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Interface {
    private JFrame ventana;
    private Mapa miMapa;
    OyenteTeclado oyente;
    Musica musica;
    private int contadorPasos;
    public Interface(int organismos, int alimentos, int matriz) throws InterruptedException, IOException {
        ventana = new JFrame();
        ventana.setTitle("Proyecto POO | Darío y Jerson");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        miMapa = new Mapa(organismos, alimentos, matriz);
        oyente = new OyenteTeclado();
        musica = new Musica();

        addComponentes();

        ventana.setFocusable(true);
        ventana.pack();
        ventana.setVisible(true);

        simular();
    }

    public void addComponentes(){
        JPanel mapaPanel = new JPanel();
        int tamanoMatriz = miMapa.getDimension().length;
        //Separando el panel en tamanoMatriz X tamanoMatriz
        mapaPanel.setLayout(new GridLayout(tamanoMatriz, tamanoMatriz));

        //Ingresando los animales
        Casilla[][] dimensionMatriz = miMapa.getDimension();        
        
        //Poniendo las casillas del tablero
        for(Casilla[] array: dimensionMatriz){
            for(Casilla elemento: array){
                (elemento.boton).setPreferredSize(new Dimension(40, 40));
                (elemento.boton).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String informacion = ((NPC) elemento.getObjeto()).getInformacion();

                        JOptionPane.showMessageDialog(null, informacion);
                        ventana.requestFocus();
                    }
                });

                if(elemento.getObjeto() != null){
                    // comparar si la clase objetoComparando es algún tipo de organismo
                    ImageIcon imagen = ((NPC) elemento.getObjeto()).setImagen();
                    (elemento.boton).setIcon(new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                }
                mapaPanel.add(elemento.boton);
            }
        }

        ventana.add(mapaPanel);
    }

    public void simular() throws InterruptedException, IOException {
        Organismo[] organismos = miMapa.getOrganismos();
        int contadorTurno = 0;
        while (miMapa.getJugando() && miMapa.organismosTienenEnergia()) {
            contadorPasos = 0;
            Organismo organismoAMoverse = organismos[contadorTurno];
            if (organismoAMoverse instanceof OrganismoJugador && organismoAMoverse.energia > 0){
                JOptionPane.showMessageDialog(null, "Turno del Jugador");
                ventana.addKeyListener(oyente); // se anade el keyListener cuando es turno del jugador
                // turno de organismoJugador
                System.out.println("Turno de jugador");
                while (contadorPasos < organismoAMoverse.velocidad && organismoAMoverse.energia > 0 && miMapa.getJugando()){
                    Thread.sleep(5);
                }

                if (organismoAMoverse.energia <= 0){
                    JOptionPane.showMessageDialog(null, "¡Te has quedado sin energia!");
                }

                ventana.removeKeyListener(oyente); // se remueve el keyListener despues de completar los pasos
            } else {
                // organismo se mueve mientras no haya completado sus pasos, mientras tenga 1 o mas de energia y mientras jugador siga en juego
                while (contadorPasos < organismoAMoverse.velocidad && organismoAMoverse.energia > 0 && miMapa.getJugando()) {
                    miMapa.moverse(organismoAMoverse); // obtiene el objeto retornado "comido" al moverse un organismo
                    ++contadorPasos;
                    // disminuye energia luego de moverse
                    organismoAMoverse.disminuirEnergia();
                    Thread.sleep(100);
                }
            }

            // aumenta la edad del organismo que acaba de moverse despues de realizar sus movimientos
            organismoAMoverse.aumentarEdad();
            organismoAMoverse.perderVision();
            ++contadorTurno;
            if (contadorTurno >= organismos.length){
                contadorTurno = 0;
            }
        }

        musica.setArchivo("sound/sonido_final.wav");
        musica.reproducir();
        if (!(miMapa.organismosTienenEnergia())){
            JOptionPane.showMessageDialog(null, "Se ha acabado el juego! " +
                    "Todos los organismos se han quedado sin energia.");
        } else {
            JOptionPane.showMessageDialog(null, "Has perdido!");
        }
    }

    //Crear la clase KeyListenes
    class OyenteTeclado extends KeyAdapter {
        //Inicializamos los eventos
        public void keyTyped(KeyEvent e) {
            //Obteniendo el org jugador para poder moverlo con las teclas
            Organismo orgJugador = miMapa.getOrganismos()[0];
            ImageIcon imagenJugador = orgJugador.setImagen();

            //Obtenes la posicion del jugador
            int posJugadorX = orgJugador.getPosition()[0];
            int posJugadorY = orgJugador.getPosition()[1];

            //Casilla donde se encuentra el organismoJugador
            Casilla[][] mapaDimension = miMapa.getDimension();
            Casilla casillaJugador = mapaDimension[posJugadorX][posJugadorY];
            char codigo = e.getKeyChar();

            //Programar para que el jugador se mueva
            if (codigo == 'w' || codigo == 'W') {
                //En este if la casilla se moverá para arriba
                if (posJugadorX <= 0) {
                    //Aqui se tira la advertencia de que no se puede mover para la arriba
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                } else {
                    Casilla siguiCasilla = mapaDimension[posJugadorX - 1][posJugadorY];
                    if (siguiCasilla.getObjeto() != null) {
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            // evalua si organismo pudo comerse al otro organismo
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                            } else {
                                miMapa.setJugando(false);
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        }
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    if (miMapa.getJugando()){
                        //Colocar el objeto en las otra casilla y la imagen
                        siguiCasilla.setObjeto(orgJugador);
                        siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                        orgJugador.setPosition(posJugadorX - 1, posJugadorY);
                        ++contadorPasos;
                        orgJugador.disminuirEnergia();
                    }
                }
            } else if (codigo == 'a' || codigo == 'A') {
                //En este if la casilla se moverá para la izquierda
                if (posJugadorY <= 0) {
                    //Aqui se tira la advertencia de que no se puede mover para la izquierda
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                } else {
                    Casilla siguiCasilla = mapaDimension[posJugadorX][posJugadorY - 1];
                    if (siguiCasilla.getObjeto() != null) {
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                            } else {
                                miMapa.setJugando(false);
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        }
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    if (miMapa.getJugando()){
                        //Colocar el objeto en las otra casilla y la imagen
                        siguiCasilla.setObjeto(orgJugador);
                        siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                        orgJugador.setPosition(posJugadorX, posJugadorY - 1);
                        ++contadorPasos;
                        orgJugador.disminuirEnergia();
                    }
                }
            } else if (codigo == 's' || codigo == 'S') {
                //En este se movera para abajo
                if (posJugadorX >= mapaDimension.length - 1) {
                    //Aqui se tira la advertencia de que no se puede mover para abajo
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                } else {
                    Casilla siguiCasilla = mapaDimension[posJugadorX + 1][posJugadorY];
                    if (siguiCasilla.getObjeto() != null) {
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                            } else {
                                miMapa.setJugando(false);
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        }
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    if (miMapa.getJugando()){
                        //Colocar el objeto en las otra casilla y la imagen
                        siguiCasilla.setObjeto(orgJugador);
                        siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                        orgJugador.setPosition(posJugadorX + 1, posJugadorY);
                        ++contadorPasos;
                        orgJugador.disminuirEnergia();
                    }
                }
            } else if (codigo == 'd' || codigo == 'D') {
                //En este se movera para la derecha
                if (posJugadorY >= mapaDimension.length - 1) {
                    //Aqui se tira la advertencia de que no se puede mover para la derecha
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                } else {
                    Casilla siguiCasilla = mapaDimension[posJugadorX][posJugadorY + 1];
                    if (siguiCasilla.getObjeto() != null) {
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                            } else {
                                miMapa.setJugando(false);
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                        }
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    if (miMapa.getJugando()){
                        //Colocar el objeto en las otra casilla y la imagen
                        siguiCasilla.setObjeto(orgJugador);
                        siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                        orgJugador.setPosition(posJugadorX, posJugadorY + 1);
                        ++contadorPasos;
                        orgJugador.disminuirEnergia();
                    }
                }
            }
        }
    }

    public class Musica{
        Clip clip;
        AudioInputStream sonido;

        public void setArchivo(String _nombreArchivoSonido){
            try {
                File archivo = new File(_nombreArchivoSonido);
                sonido = AudioSystem.getAudioInputStream(archivo);
                clip = AudioSystem.getClip();
                clip.open(sonido);
            } catch (Exception e){

            }
        }

        public void reproducir(){
            // le asigna como al atributo clip la bandera de que esta iniciada la musica
            clip.start();
        }

        public void reproducirLoop(){
            // inicia el loop de la musica
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // le asigna al atributo clip la bandera de que esta iniciada la musica
            clip.start();
        }

        public void detener() throws IOException {
            // cierra la comunicacion con el archivo de sonido
            sonido.close();
            // cierra el audio
            clip.close();
            // detiene la musica, y le asigna la bandera de false a clip
            clip.stop();
        }
    }
}