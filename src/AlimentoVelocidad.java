import javax.swing.ImageIcon;

public class AlimentoVelocidad extends Alimento{
    private int velocidad;

    public int getAtributo() {
        return velocidad;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("limon.png");
        return imagen;
    }
}
