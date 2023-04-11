import javax.swing.ImageIcon;
import java.util.Random;

public class OrganismoVelocidad extends Organismo{

    public OrganismoVelocidad(){
        super();
    }

    // getters
    public int[] getPosition(){
        return this.pos;
    }

    public String getInformacion(){
        return "Organismo Velocidad\n" + 
        "\tEnergia: " + energia + "\n" + 
        "\tVision: " + vision + "\n" + 
        "\tVelocidad: " + velocidad + "\n" +
        "\tEdad: " + edad;
    }

    // setters
    public void setPosition(int i, int j){
        this.pos[0] = i;
        this.pos[1] = j;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/tortuga-marina.png");
        return imagen;
    }
}
