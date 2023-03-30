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

        //Ingresando los animales
        Casilla[][] dimensionMatriz = miMapa.getDimension();        
        
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
        Casilla casillaNuevoObjeto;
        Organismo[] organismos = miMapa.getOrganismos();
        Alimento[] alimentos = miMapa.getAlimentos();
        Object objetoComido;
        int[] posicionOrganismo;
        int[] posicionNuevoObjeto;
        int indiceNuevoAlimento;
        int contadorTurno = 0;
        while (true) {
            contadorPasos = 0;
            if (organismos[contadorTurno] instanceof OrganismoJugador){
                ventana.addKeyListener(oyente); // se anade el keyListener cuando es turno del jugador
                // turno de organismoJugador
                System.out.println("Turno de jugador");
                while (contadorPasos < organismos[contadorTurno].velocidad && organismos[contadorTurno].energia > 0){
                    Thread.currentThread().sleep(5);
                }

                ventana.removeKeyListener(oyente); // se remueve el keyListener despues de completar los pasos
            } else {
                Organismo organismoAMoverse = organismos[contadorTurno];
                // organismo se mueve mientras no haya completado sus pasos y mientras tenga 1 o mas de energia
                while (contadorPasos < organismos[contadorTurno].velocidad && organismos[contadorTurno].energia > 0) {
                    objetoComido = miMapa.moverse(organismoAMoverse); // obtiene el objeto retornado "comido" al moverse un organismo
                    if (objetoComido instanceof Organismo){
                        posicionNuevoObjeto = organismos[organismos.length - 1].getPosition(); // posicion del ultimo organismo creado
                        String infoNuevoOrg = organismos[organismos.length - 1].getInformacion(); 
                        casillaNuevoObjeto = matriz[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        casillaNuevoObjeto.boton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, infoNuevoOrg);
                                ventana.requestFocus();
                            }
                        });

                    } else if (objetoComido instanceof Alimento) {
                        indiceNuevoAlimento = miMapa.getIndiceAlimento(); // toma valor del indice del ultimo AlimentoCreado
                        posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                        String infoNuevoAlim = alimentos[indiceNuevoAlimento].getInformacion();
                        casillaNuevoObjeto = matriz[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        casillaNuevoObjeto.boton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, infoNuevoAlim);
                                ventana.requestFocus();
                            }
                        });

                    }

                    String infoOrgJugador = organismoAMoverse.getInformacion();
                    posicionOrganismo = organismoAMoverse.getPosition();
                    casillaOrganismo = matriz[posicionOrganismo[0]][posicionOrganismo[1]];

                    casillaOrganismo.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                    Thread.sleep(1000);
                }

            }

            ++contadorTurno;
            if (contadorTurno >= organismos.length){
                contadorTurno = 0;
            }
        }
    }

    //Crear la clase KeyListenes
    class OyenteTeclado extends KeyAdapter {
        //Inicializamos los eventos
        public void keyTyped(KeyEvent e) {
            //Obteniendo el org jugador para poder moverlo con las teclas
            Organismo orgJugador = miMapa.getOrganismos()[0];
            ImageIcon imagenJugador = orgJugador.setImagen();
            String infoOrgJugador = orgJugador.getInformacion();

            // objeto comido al moverse
            Object objetoComido;

            // posicion del nuevoObjeto creado al comerse uno
            int[] posicionNuevoObjeto;

            // indice donde se crea el nuevo alimento
            int indiceNuevoAlimento;

            // casilla donde se ubica el nuevo objeto
            Casilla casillaNuevoObjeto = null;

            // obtener array de organismo y alimento
            Organismo[] organismos = miMapa.getOrganismos();
            Alimento[] alimentos = miMapa.getAlimentos();

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
                        // inicializacion de informacion en "" por necesidad que necesita estar inicializado
                        String infoNuevoObjeto = "";
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                                posicionNuevoObjeto = organismos[organismos.length - 1].getPosition();
                                infoNuevoObjeto = organismos[organismos.length - 1].getInformacion();
                                casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        }

                        // variable final de infoNuevoObjeto debido a que actionPerformed pide variable final
                        String finalInfoNuevoObjeto = infoNuevoObjeto;
                        casillaNuevoObjeto.boton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, finalInfoNuevoObjeto);
                                ventana.requestFocus();
                            }
                        });
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX - 1, posJugadorY);
                    siguiCasilla.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                }
            } else if (codigo == 'a' || codigo == 'A') {
                //En este if la casilla se moverá para la izquierda
                if (posJugadorY <= 0) {
                    //Aqui se tira la advertencia de que no se puede mover para la izquierda
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                } else {
                    Casilla siguiCasilla = mapaDimension[posJugadorX][posJugadorY - 1];
                    if (siguiCasilla.getObjeto() != null) {
                        // inicializacion de informacion en "" por necesidad que necesita estar inicializado
                        String infoNuevoObjeto = "";
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                                posicionNuevoObjeto = organismos[organismos.length - 1].getPosition();
                                infoNuevoObjeto = organismos[organismos.length - 1].getInformacion();
                                casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        }

                        // variable final de infoNuevoObjeto debido a que actionPerformed pide variable final
                        String finalInfoNuevoObjeto = infoNuevoObjeto;
                        casillaNuevoObjeto.boton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, finalInfoNuevoObjeto);
                                ventana.requestFocus();
                            }
                        });
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX, posJugadorY - 1);
                    siguiCasilla.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                }
            } else if (codigo == 's' || codigo == 'S') {
                //En este se movera para abajo
                if (posJugadorX >= 49) {
                    //Aqui se tira la advertencia de que no se puede mover para abajo
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                } else {
                    Casilla siguiCasilla = mapaDimension[posJugadorX + 1][posJugadorY];
                    if (siguiCasilla.getObjeto() != null) {
                        // inicializacion de informacion en "" por necesidad que necesita estar inicializado
                        String infoNuevoObjeto = "";
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                                posicionNuevoObjeto = organismos[organismos.length - 1].getPosition();
                                infoNuevoObjeto = organismos[organismos.length - 1].getInformacion();
                                casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        }

                        // variable final de infoNuevoObjeto debido a que actionPerformed pide variable final
                        String finalInfoNuevoObjeto = infoNuevoObjeto;
                        casillaNuevoObjeto.boton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, finalInfoNuevoObjeto);
                                ventana.requestFocus();
                            }
                        });
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX + 1, posJugadorY);
                    siguiCasilla.boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, infoOrgJugador);
                            ventana.requestFocus();
                        }
                    });

                    ++contadorPasos;
                }
            } else if (codigo == 'd' || codigo == 'D') {
                //En este se movera para la derecha
                if (posJugadorY >= 49) {
                    //Aqui se tira la advertencia de que no se puede mover para la derecha
                    JOptionPane.showMessageDialog(null, "No se puede mover para esa dirección");
                } else {
                    Casilla siguiCasilla = mapaDimension[posJugadorX][posJugadorY + 1];
                    if (siguiCasilla.getObjeto() != null) {
                        // inicializacion de informacion en "" por necesidad que necesita estar inicializado
                        String infoNuevoObjeto = "";
                        if (siguiCasilla.getObjeto() instanceof Organismo) {
                            Organismo organismoAAtacar = (Organismo) siguiCasilla.getObjeto();
                            if (orgJugador.atacar(organismoAAtacar)) {
                                miMapa.eliminarOrganismo(organismoAAtacar);
                                posicionNuevoObjeto = organismos[organismos.length - 1].getPosition();
                                infoNuevoObjeto = organismos[organismos.length - 1].getInformacion();
                                casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                            }

                        } else if (siguiCasilla.getObjeto() instanceof AlimentoEnergia) {
                            AlimentoEnergia alimentoAComer = (AlimentoEnergia) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVelocidad) {
                            AlimentoVelocidad alimentoAComer = (AlimentoVelocidad) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        } else if (siguiCasilla.getObjeto() instanceof AlimentoVision) {
                            AlimentoVision alimentoAComer = (AlimentoVision) siguiCasilla.getObjeto();
                            orgJugador.atacar(alimentoAComer);
                            miMapa.eliminarAlimento(alimentoAComer);
                            indiceNuevoAlimento = miMapa.getIndiceAlimento();
                            posicionNuevoObjeto = alimentos[indiceNuevoAlimento].getPosition();
                            infoNuevoObjeto = alimentos[indiceNuevoAlimento].getInformacion();
                            casillaNuevoObjeto = mapaDimension[posicionNuevoObjeto[0]][posicionNuevoObjeto[1]];
                        }

                        // variable final de infoNuevoObjeto debido a que actionPerformed pide variable final
                        String finalInfoNuevoObjeto = infoNuevoObjeto;
                        casillaNuevoObjeto.boton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, finalInfoNuevoObjeto);
                                ventana.requestFocus();
                            }
                        });
                    }

                    casillaJugador.boton.setIcon(null); //Le quitamos la imagen
                    casillaJugador.setObjeto(); //Le quitamos el objeto y lo desabilitamos

                    //Colocar el objeto en las otra casilla y la imagen
                    siguiCasilla.setObjeto(orgJugador);
                    siguiCasilla.boton.setIcon(new ImageIcon(imagenJugador.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
                    orgJugador.setPosition(posJugadorX, posJugadorY + 1);
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
