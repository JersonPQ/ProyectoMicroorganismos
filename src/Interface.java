import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Interface {
    private JFrame ventana;
    private Mapa miMapa = new Mapa();
    private ImageIcon imagen;
    private String informacion;

    private Organismo[] organismos;
    private int[] indiceEncontrado;
    private int[] posicionOrganismo;

    public Interface(){
        ventana = new JFrame();
        ventana.setTitle("Proyecto POO | Darío y Jerson");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OyenteTeclado oyente = new OyenteTeclado();
        ventana.addKeyListener(oyente);

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

    public void simular(){
        organismos = miMapa.getOrganismos();
        for (int i = 1; i < organismos.length; ++i){
            if (organismos[i] instanceof OrganismoVelocidad){
                posicionOrganismo = organismos[i].getPosicion();
                System.out.println("Organismo velocidad en la posicion (" + posicionOrganismo[0] + ", " + posicionOrganismo[1] + ")");
                indiceEncontrado = miMapa.busqueda((OrganismoVelocidad) organismos[i]);
                System.out.println(indiceEncontrado[0] + ", " + indiceEncontrado[1]);
            } else if (organismos[i] instanceof OrganismoVision) {
                posicionOrganismo = organismos[i].getPosicion();
                System.out.println("Organismo vision en la posicion (" + posicionOrganismo[0] + ", " + posicionOrganismo[1] + ")");
                indiceEncontrado = miMapa.busqueda((OrganismoVision) organismos[i]);
                System.out.println(indiceEncontrado[0] + ", " + indiceEncontrado[1]);
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
                }
            }
        }
    }
}
