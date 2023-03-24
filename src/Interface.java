import javax.swing.*;
import java.awt.*;
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

        addComponentes();

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
        OyenteTeclado oyente = new OyenteTeclado();
        
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
    class OyenteTeclado implements KeyListener{
        //Obteniendo el org jugador para poder moverlo con las teclas
        Organismo orgJugador = miMapa.getOrganismos()[0];

        //Obtenes la posicion del jugador
        int posJugadorX = orgJugador.getPosicion()[0];
        int posJugadorY = orgJugador.getPosicion()[1];

        //Casilla donde se encuentra el organismoJugador
        Casilla[][] mapaDimension = miMapa.getDimension();
        Casilla casillaJugador = mapaDimension[posJugadorX][posJugadorY];
        
        //Inicializamos los eventos
        public void keyTyped(KeyEvent e){
            int codigo = e.getKeyCode();
            //Programar para que el jugador se mueva
            //Se tiene que validar para que no se pueda salir de la dimension del mapa
            if(codigo == 87 || codigo == 119){
                //En este if la casilla se moverá para adelante
            }
            else if(codigo == 65 || codigo == 97){
                //En este if la casilla se moverá para la izquierda
            }
            else if(codigo == 83 || codigo == 115){
                //En este se movera para abajo
            }
            else if(codigo == 68 || codigo == 100){
                //En este se movera para la derecha
            }
        }

        //Este es el que vamos a usar
        public void keyPressed(KeyEvent e){

        }

        public void keyReleased(KeyEvent e){

        }
    }
}
