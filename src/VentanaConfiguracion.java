import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaConfiguracion {
    private JFrame Ventana;
    private JLabel Titulo;
    private JLabel labelMaxEnergy;
    private JLabel labelMaxVelocidad;
    private JLabel labelMaxVision;
    private JLabel labelMatriz;
    private JLabel labelOrganismos;
    private JLabel labelAlimentos;
    private JComboBox<Integer> comboMaxEnergy;
    private JComboBox<Integer> comboMaxVelocidad;
    private JComboBox<Integer> comboMaxVision;
    private JComboBox<Integer> comboMatriz;
    private JComboBox<Integer> comboOrganismos;
    private JComboBox<Integer> comboAlimentos;
    private JButton botonJuego;
    private Interface juego;
    private boolean bandera = false;
    int maxEnergia;
    int maxVelocidad;
    int maxVision;
    int tamanoMatriz;
    int cantOrganismos;
    int cantAlimentos;

    public VentanaConfiguracion() throws InterruptedException{
        //Ponemos las configuraciones básicas a la ventan
        Ventana = new JFrame("Ventana de configuración");
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana.setLayout(new FlowLayout());
        Ventana.setVisible(true);

        addComponentes();
        Ventana.pack();
        while(true){
            Thread.sleep(1);
            if(bandera){
                Ventana.setVisible(false);
                juego = new Interface(cantOrganismos, cantAlimentos, tamanoMatriz);
                break;
            }
        }
    }

    public void addComponentes() throws InterruptedException{
        JPanel izquierda = new JPanel();
        JPanel centro = new JPanel();

        //Establecemos el Layout para los JPanel
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        centro.setLayout(new BoxLayout(centro, BoxLayout.PAGE_AXIS  ));

        //Establecemos los comboBox
        comboMaxEnergy = new JComboBox<Integer>();
        comboMaxVelocidad = new JComboBox<Integer>();
        comboMaxVision = new JComboBox<Integer>();
        comboMatriz = new JComboBox<Integer>();
        comboOrganismos = new JComboBox<Integer>();
        comboAlimentos = new JComboBox<Integer>();

        //Espaciadores
        Component espaciadorBoton = Box.createVerticalStrut(10);

        //Poniendo el Titulo
        Titulo = new JLabel("Configuración");
        Titulo.setFont(new Font("Arial", Font.BOLD, 30));
        centro.add(Titulo);

        //Inicializando boton
        botonJuego = new JButton("Crear juego");

        //Items del comboMaxEnergy
        comboMaxEnergy.addItem(30);
        comboMaxEnergy.addItem(40);
        comboMaxEnergy.addItem(50);
        comboMaxEnergy.setSize(500, 500);
        centro.add(comboMaxEnergy);

        //Items del comboMaxVelocidad
        comboMaxVelocidad.addItem(30);
        comboMaxVelocidad.addItem(40);
        comboMaxVelocidad.addItem(50);
        centro.add(comboMaxVelocidad);

        //Items del comboMaxVision
        comboMaxVision.addItem(30);
        comboMaxVision.addItem(40);
        comboMaxVision.addItem(50);
        centro.add(comboMaxVision);

        //Items del comboMatriz
        comboMatriz.addItem(50);
        comboMatriz.addItem(55);
        comboMatriz.addItem(60);
        centro.add(comboMatriz);

        //Items del comboOrganismos
        comboOrganismos.addItem(40);
        comboOrganismos.addItem(25);
        comboOrganismos.addItem(30);
        centro.add(comboOrganismos);

        //Items del comboAlimentos
        comboAlimentos.addItem(40);
        comboAlimentos.addItem(45);
        comboAlimentos.addItem(50);
        centro.add(comboAlimentos);
        centro.add(espaciadorBoton);
        centro.add(botonJuego);

        //Vamos a configurar el lado izquierdo
        labelMaxEnergy = new JLabel("Energía máxima:");
        labelMaxVelocidad = new JLabel("Velocidad máxima:");
        labelMaxVision = new JLabel("Visión máxima:");
        labelMatriz = new JLabel("Tamaño de la matriz:");
        labelOrganismos = new JLabel("Cantidad de organismos:");
        labelAlimentos = new JLabel("Cantidad de alimentos:");

        //Colocamos fuentes y tamaño a los label
        labelMaxEnergy.setFont(new Font("Arial", Font.PLAIN, 20));
        labelMaxVelocidad.setFont(new Font("Arial", Font.PLAIN, 20));
        labelMaxVision.setFont(new Font("Arial", Font.PLAIN, 20));
        labelMatriz.setFont(new Font("Arial", Font.PLAIN, 20));
        labelOrganismos.setFont(new Font("Arial", Font.PLAIN, 20));
        labelAlimentos.setFont(new Font("Arial", Font.PLAIN, 20));

        izquierda.add(labelMaxEnergy);
        izquierda.add(labelMaxVelocidad);
        izquierda.add(labelMaxVision);
        izquierda.add(labelMatriz);
        izquierda.add(labelOrganismos);
        izquierda.add(labelAlimentos);

        Ventana.add(izquierda);
        Ventana.add(centro);

        //Colocar el ActionListenes
        botonJuego.addActionListener(new oyenteIncia());
    }

    class oyenteIncia implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            maxEnergia = (int)comboMaxEnergy.getSelectedItem();
            maxVelocidad = (int)comboMaxVelocidad.getSelectedItem();
            maxVision = (int)comboMaxVision.getSelectedItem();
            tamanoMatriz = (int)comboMatriz.getSelectedItem();
            cantOrganismos = (int)comboOrganismos.getSelectedItem();
            cantAlimentos = (int)comboAlimentos.getSelectedItem();
            bandera = true;
        }
    }
}