import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Configuracion {
    private JFrame Ventana;
    private JLabel Titulo;
    private JLabel labelMaxEnergy;
    private JLabel labelMaxVelocidad;
    private JLabel labelMaxVision;
    private JLabel labelMatriz;
    private JLabel labelOrganismos;
    private JLabel labelAlimentos;
    private JLabel labelEnergia;
    private JLabel labelVelocidad;
    private JLabel labelVision;
    private JComboBox<Integer> comboMaxEnergy;
    private JComboBox<Integer> comboMaxVelocidad;
    private JComboBox<Integer> comboMaxVision;
    private JComboBox<Integer> comboMatriz;
    private JComboBox<Integer> comboOrganismos;
    private JComboBox<Integer> comboAlimentos;
    private JComboBox<Integer> comboEnergia;
    private JComboBox<Integer> comboVelocidad;
    private JComboBox<Integer> comboVision;
    private JButton botonJuego;
    private Interface juego;
    private boolean bandera = false;
    static int maxEnergia;
    static int maxVelocidad;
    static int maxVision;
    static int energia;
    static int velocidad;
    static int vision;

    int tamanoMatriz;
    int cantOrganismos;
    int cantAlimentos;

    public Configuracion() throws InterruptedException{
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
        comboEnergia = new JComboBox<Integer>();
        comboVelocidad = new JComboBox<Integer>();
        comboVision = new JComboBox<Integer>();

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
        comboMaxVelocidad.addItem(10);
        comboMaxVelocidad.addItem(15);
        comboMaxVelocidad.addItem(20);
        centro.add(comboMaxVelocidad);

        //Items del comboMaxVision
        comboMaxVision.addItem(5);
        comboMaxVision.addItem(10);
        comboMaxVision.addItem(15);
        centro.add(comboMaxVision);

        //Items del comboMatriz
        for (int i = 50; i <= 60; i = i + 5) {
            comboMatriz.addItem(i);
        }
        centro.add(comboMatriz);

        //Items del comboOrganismos
        for (int i = 30; i <= 50; i = i + 5) {
            comboOrganismos.addItem(i);
        }
        centro.add(comboOrganismos);

        //Items del comboAlimentos
        for (int i = 40; i <= 100; i = i + 5) {
            comboAlimentos.addItem(i);
        }
        centro.add(comboAlimentos);

        //Items del comboEnergia
        for (int i = 1; i <= 5; i++) {
            comboEnergia.addItem(i);
        }
        centro.add(comboEnergia);

        //Items del comboVelocidad
        comboVelocidad.addItem(1);
        comboVelocidad.addItem(2);
        comboVelocidad.addItem(3);
        centro.add(comboVelocidad);

        //Items del comboVision
        comboVision.addItem(1);
        comboVision.addItem(2);
        comboVision.addItem(3);
        centro.add(comboVision);

        centro.add(espaciadorBoton);
        centro.add(botonJuego);

        //Vamos a configurar el lado izquierdo
        labelMaxEnergy = new JLabel("Energía máxima:");
        labelMaxVelocidad = new JLabel("Velocidad máxima:");
        labelMaxVision = new JLabel("Visión máxima:");
        labelMatriz = new JLabel("Tamaño de la matriz:");
        labelOrganismos = new JLabel("Cantidad de organismos:");
        labelAlimentos = new JLabel("Cantidad de alimentos:");
        labelEnergia = new JLabel("Cantidad maxima de energia por alimento:");
        labelVelocidad = new JLabel("Cantidad de velocidad por alimento:");
        labelVision = new JLabel("Cantidad de vision por alimento:");

        //Colocamos fuentes y tamaño a los label
        labelMaxEnergy.setFont(new Font("Arial", Font.PLAIN, 20));
        labelMaxVelocidad.setFont(new Font("Arial", Font.PLAIN, 20));
        labelMaxVision.setFont(new Font("Arial", Font.PLAIN, 20));
        labelMatriz.setFont(new Font("Arial", Font.PLAIN, 20));
        labelOrganismos.setFont(new Font("Arial", Font.PLAIN, 20));
        labelAlimentos.setFont(new Font("Arial", Font.PLAIN, 20));
        labelEnergia.setFont(new Font("Arial", Font.PLAIN, 20));
        labelVelocidad.setFont(new Font("Arial", Font.PLAIN, 20));
        labelVision.setFont(new Font("Arial", Font.PLAIN, 20));

        izquierda.add(labelMaxEnergy);
        izquierda.add(labelMaxVelocidad);
        izquierda.add(labelMaxVision);
        izquierda.add(labelMatriz);
        izquierda.add(labelOrganismos);
        izquierda.add(labelAlimentos);
        izquierda.add(labelEnergia);
        izquierda.add(labelVelocidad);
        izquierda.add(labelVision);

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
            energia = (int)comboEnergia.getSelectedItem();
            velocidad = (int)comboVelocidad.getSelectedItem();
            vision = (int)comboVision.getSelectedItem();
            bandera = true;
        }
    }
}