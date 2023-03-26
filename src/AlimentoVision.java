import javax.swing.ImageIcon;

public class AlimentoVision extends Alimento{
    private int vision;

    public AlimentoVision(){
        vision = 1;
    }

    public int getAtributo() {
        return vision;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/manzana.png");
        return imagen;
    }

    public String getInformacion(){
        return "Alimento Visión\n" + 
        "\tVisión brindada: " + vision;
    }
}
