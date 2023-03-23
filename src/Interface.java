import javax.swing.*;
import java.awt.*;

public class Interface {
    private JFrame ventana;
    private Mapa miMapa;
    private ImageIcon imagen;

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

        miMapa = new Mapa();

        //Ingresando los animales
        Casilla[][] dimensionMatriz = miMapa.getDimension();
        
        //Poniendo las casillas del tablero
        for(Casilla[] array: miMapa.getDimension()){
            for(Casilla elemento: array){
                (elemento.boton).setPreferredSize(new Dimension(100, 100));
                if(elemento.getObjeto() != null){
                    imagen = elemento.getObjeto().setImagen();
                    (elemento.boton).setIcon(new ImageIcon(imagen.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));   
                }
                mapaPanel.add(elemento.boton);
            }
        }
        ventana.add(mapaPanel);
    }
}