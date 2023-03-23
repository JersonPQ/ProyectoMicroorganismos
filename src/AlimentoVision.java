import javax.swing.ImageIcon;

public class AlimentoVision extends Alimento{
    private int vision;

    public int getAtributo() {
        return vision;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("manazana.png");
        return imagen;
    }
}
