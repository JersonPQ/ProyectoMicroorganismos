import javax.swing.ImageIcon;

public class AlimentoVelocidad extends Alimento{
    private int velocidad;

    public int getAtributo() {
        return velocidad;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/limon.png");
        return imagen;
    }

    public String getInformacion(){
        return "Alimento Velocidad\n" + 
        "\tVelocidad brindada: " + velocidad;
    }
}
