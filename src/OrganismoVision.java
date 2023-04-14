import javax.swing.ImageIcon;
import java.util.Random;

public class OrganismoVision extends Organismo{

    public OrganismoVision(){
        super();
    }

    // getters
    public int[] getPosition(){
        return this.pos;
    }
    
    public String getInformacion(){
        return "Organismo Vision\n" + 
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
        ImageIcon imagen = new ImageIcon("images/murcielago.png");
        return imagen;
    }
}
