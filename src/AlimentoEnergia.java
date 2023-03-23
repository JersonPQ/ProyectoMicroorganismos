import javax.swing.ImageIcon;

public class AlimentoEnergia extends Alimento{
    private int energia;

    public int getAtributo() {
        return energia;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/fresa.png");
        return imagen;
    }
}
