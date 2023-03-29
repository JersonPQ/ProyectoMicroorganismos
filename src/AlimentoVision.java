import javax.swing.ImageIcon;

public class AlimentoVision extends Alimento{
    private int vision;

    public AlimentoVision(){
        vision = 1;
        this.pos = new int[2];
    }

    // getters
    public int getAtributo() {
        return vision;
    }

    public String getInformacion(){
        return "Alimento Visión\n" +
                "\tVisión brindada: " + vision;
    }

    public int[] getPosition() {
        return pos;
    }

    // setters
    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/manzana.png");
        return imagen;
    }

    public void setPosition(int i, int j) {
        this.pos[0] = i;
        this.pos[1] = j;
    }
}
