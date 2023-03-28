import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Interface {
    private JFrame ventana;
    private Mapa miMapa = new Mapa();
    OyenteTeclado oyente;
    private ImageIcon imagen;
    private String informacion;
    private int contadorPasos;
    public Interface() throws InterruptedException{
        ventana = new JFrame();
        ventana.setTitle("Proyecto POO | Darío y Jerson");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        oyente = new OyenteTeclado();

        addComponentes();

        ventana.setFocusable(true);
        ventana.pack();
        ventana.setVisible(true);

        simular();
    }

    public void addComponentes(){
        //Separando el panel en 50x50
        JPanel mapaPanel = new JPanel();
        mapaPanel.setLayout(new GridLayout(50, 50));

        //Inicializar objetos a Comparar


        //Ingresando los animales
        Casilla[][] dimensionMatriz = miMapa.getDimension();

        //Inicializando la clase oyente
        
        
        //Poniendo las casillas del tablero
        for(Casilla[] array: dimensionMatriz){
            for(Casilla elemento: array){
                (elemento.boton).setPreferredSize(new Dimension(40, 40));
                if(elemento.getObjeto() != null){
                    // comparar si la clase objetoComparando es algún tipo de organismo
                    imagen = ((NPC) elemento.getObjeto()).setImagen();
                    (elemento.boton).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            informacion = ((NPC) elemento.getObjeto()).getInformacion();
                        
                            JOptionPane.showMessageDialog(null, informacion);
                            ventana.requestFocus();
                        }
                    });
                    (elemento.boton).setIcon(new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));   
                }
                mapaPanel.add(elemento.boton);
            }
        }

        ventana.add(mapaPanel);
    }

    public void simular() throws InterruptedException{
        Casilla[][] matriz = miMapa.getDimension();
        Casilla casillaOrganismo;
        int[] posicionOrganismo;
        Organismo[] organismos = miMapa.getOrganismos();
        int contadorTurno = 0;
        while (true) {
            contadorPasos = 0;
            if (organismos[contadorTurno] instanceof OrganismoJugador){
                ventana.addKeyListener(oyente);
                // turno de organismoJugador
                System.out.println("Turno de jugador");
                while (contadorPasos < organismos[contadorTurno].velocidad){
                    Thread.currentThread().sleep(5);
                }

                ventana.removeKeyListener(oyente);
            } else {
                Organismo organismoAMoverse = organismos[contadorTurno];
                while (contadorPasos < organismos[contadorTurno].velocidad) {
                    miMapa.moverse(organismoAMoverse);
                    String infoOrgJugador = organismoAMoverse.getInformacion();
                    posicionOrganismo = organismoAMoverse.getPosicion();
                    casillaOrganismo = matriz[posicionOrganismo[0]][posicionOrganismo[1]];

                    casillaOrganismo.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });
                    Thread.sleep(1000);
                    ++contadorPasos;
                }

            }

            ++contadorTurno;
            if (contadorTurno >= organismos.length){
                contadorTurno = 0;
            }
        }
    }

    //Crear la clase KeyListenes
    class OyenteTeclado extends KeyAdapter{
            //Inicializamos los eventos
            public void keyTyped(KeyEvent e){
            //Obteniendo el org jugador para poder moverlo con las teclas
            Organismo orgJugador = miMapa.getOrganismos()[0];
            ImageIcon imagenJugador = orgJugador.setImagen();
            String infoOrgJugador = orgJugador.getInformacion();

            //Obtenes la posicion del jugador
            int posJugadorX = orgJugador.getPosicion()[0];
            int posJugadorY = orgJugador.getPosicion()[1];

            //Casilla donde se encuentra el organismoJugador
            Casilla[][] mapaDimension = miMapa.getDimension();
            Casilla casillaJugador = mapaDimension[posJugadorX][posJugadorY];
            char codigo = e.getKeyChar();

            //Programar para que el jugador se mueva
            if(codigo == 'w' || codigo == 'W'){
                //En este if la casilla se moverá para arriba
                if(posJugadorX <= 0){
                    //Aqui se tira la advertencia de que no se puede mover para la arriba
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                }
                else{
                    Casilla siguiCasilla = mapaDimension[posJugadorX-1][posJugadorY];
                    if (siguiCasilla.getObjeto() != null){
                        if (siguiCasilla.getObjeto() instanceof Organismo){
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)){
                                miMapa.eliminarOrganismo(organismoAAtacar);
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

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX-1, posJugadorY);
                    siguiCasilla.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                }
            }
            else if(codigo == 'a' || codigo == 'A'){
                //En este if la casilla se moverá para la izquierda
                if(posJugadorY <= 0){
                    //Aqui se tira la advertencia de que no se puede mover para la izquierda
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                }
                else{
                    Casilla siguiCasilla = mapaDimension[posJugadorX][posJugadorY-1];
                    if (siguiCasilla.getObjeto() != null){
                        if (siguiCasilla.getObjeto() instanceof Organismo){
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            orgJugador.atacar(organismoAAtacar);
                            if (orgJugador.atacar(organismoAAtacar)){
                                miMapa.eliminarOrganismo(organismoAAtacar);
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

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX, posJugadorY-1);
                    siguiCasilla.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                }
            }
            else if(codigo == 's' || codigo == 'S'){
                //En este se movera para abajo
                if(posJugadorX >= 49){
                    //Aqui se tira la advertencia de que no se puede mover para abajo
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                }
                else{
                    Casilla siguiCasilla = mapaDimension[posJugadorX+1][posJugadorY];
                    if (siguiCasilla.getObjeto() != null){
                        if (siguiCasilla.getObjeto() instanceof Organismo){
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            orgJugador.atacar(organismoAAtacar);
                            if (orgJugador.atacar(organismoAAtacar)){
                                miMapa.eliminarOrganismo(organismoAAtacar);
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

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX+1, posJugadorY);
                    siguiCasilla.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                }
            }
            else if(codigo == 'd' || codigo == 'D'){
                //En este se movera para la derecha
                if(posJugadorY >= 49){
                    //Aqui se tira la advertencia de que no se puede mover para la derecha
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                }
                else{
                    Casilla siguiCasilla = mapaDimension[posJugadorX][posJugadorY+1];
                    if (siguiCasilla.getObjeto() != null){
                        if (siguiCasilla.getObjeto() instanceof Organismo){
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            orgJugador.atacar(organismoAAtacar);
                            if (orgJugador.atacar(organismoAAtacar)){
                                miMapa.eliminarOrganismo(organismoAAtacar);
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

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX, posJugadorY+1);
                    siguiCasilla.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                }
            }
        }
    }
}
