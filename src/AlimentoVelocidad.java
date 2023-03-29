import javax.swing.ImageIcon;

public class AlimentoVelocidad extends Alimento{
    private int velocidad;

    public AlimentoVelocidad(){
        velocidad = 1;
        this.pos = new int[2];
    }

    // getters
    public int getAtributo() {
        return velocidad;
    }

    public String getInformacion(){
        return "Alimento Velocidad\n" +
                "\tVelocidad brindada: " + velocidad;
    }

    public int[] getPosition(){
        return pos;
    }

    // setters
    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/limon.png");
        return imagen;
    }

    public void setPosition(int i, int j){
        this.pos[0] = i;
        this.pos[1] = j;
    }
}
