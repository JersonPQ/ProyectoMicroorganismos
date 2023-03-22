import javax.swing.*;
import java.awt.*;

public class Interface {
    private JFrame ventana;
    private Mapa miMapa;

    public Interface(){
        ventana = new JFrame();
        ventana.setTitle("Proyecto POO | Darío y Jerson");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentes();

        ventana.pack();
        ventana.setVisible(true);
    }

    public void addComponentes(){
        JPanel mapaPanel = new JPanel();
        mapaPanel.setLayout(new GridLayout(50, 50));

        miMapa = new Mapa();
        for(Casilla[] array: miMapa.getDimension()){
            for(Casilla elemento: array){
                (elemento.boton).setPreferredSize(new Dimension(40, 40));
                mapaPanel.add(elemento.boton);
            }
        }
        ventana.add(mapaPanel, BorderLayout.CENTER);
    }
}
