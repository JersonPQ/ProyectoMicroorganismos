import javax.swing.ImageIcon;
import java.util.Random;

public class OrganismoJugador extends Organismo{
//    private boolean jugadorJugando = true;

    public OrganismoJugador(){
        super();
    }

    // getters
    public int[] getPosition(){
        return this.pos;
    }

    public String getInformacion(){
        return "Organismo Jugador\n" +
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
        ImageIcon imagen = new ImageIcon("images/leon.png");
        return imagen;
    }
}
