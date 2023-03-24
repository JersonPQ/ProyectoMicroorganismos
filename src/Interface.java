import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Interface {
    private JFrame ventana;
    private Mapa miMapa = new Mapa();;
    private ImageIcon imagen;
    private Object objetoComparando;
    private OrganismoJugador organismoJugadorComparar;
    private OrganismoVelocidad organismoVelocidadComparar;
    private OrganismoVision organismoVisionComparar;


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
    }

    public void addComponentes(){
        //Separando el panel en 50x50
        JPanel mapaPanel = new JPanel();
        mapaPanel.setLayout(new GridLayout(50, 50));

        //Inicializar objetos a Comparar
        organismoJugadorComparar = new OrganismoJugador();
        organismoVelocidadComparar = new OrganismoVelocidad();
        organismoVisionComparar = new OrganismoVision();

        //Ingresando los animales
        Casilla[][] dimensionMatriz = miMapa.getDimension();

        //Inicializando la clase oyente
        
        
        //Poniendo las casillas del tablero
        for(Casilla[] array: dimensionMatriz){
            for(Casilla elemento: array){
                (elemento.boton).setPreferredSize(new Dimension(40, 40));
                if(elemento.getObjeto() != null){
                    objetoComparando = elemento.getObjeto().getClass();
                    // comparar si la clase objetoComparando es algún tipo de organismo
                    if (objetoComparando == organismoJugadorComparar.getClass() || objetoComparando == organismoVelocidadComparar.getClass() || objetoComparando == organismoVisionComparar.getClass()){
                        imagen = ((Organismo) elemento.getObjeto()).setImagen();
                        
                    } else {
                        imagen = ((Alimento) elemento.getObjeto()).setImagen();
                    }

                    (elemento.boton).setIcon(new ImageIcon(imagen.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));   
                }
                mapaPanel.add(elemento.boton);
            }
        }

        ventana.add(mapaPanel);
    }

    //Crear la clase KeyListenes
    class OyenteTeclado extends KeyAdapter{
            //Inicializamos los eventos
            public void keyTyped(KeyEvent e){
                
            //Obteniendo el org jugador para poder moverlo con las teclas
            Organismo orgJugador = miMapa.getOrganismos()[0];
            ImageIcon imagenJugador = orgJugador.setImagen();

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
                }
            }
        }
    }
}
