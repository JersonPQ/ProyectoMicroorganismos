import javax.swing.ImageIcon;
import java.util.Random;

public class AlimentoEnergia extends Alimento{
    private int energia;

    public AlimentoEnergia(){
        Random rnd = new Random();
        // random de energia entre 1 y el maximo dado en configuracion
        energia = rnd.nextInt(Configuracion.energia) + 1; ;
        this.pos = new int[2];
    }

    // getters
    public int getAtributo() {
        return energia;
    }

    public String getInformacion(){
        return "Alimento Energía\n" +
                "\tEnergia brindada: " + energia;
    }

    public int[] getPosition(){
        return pos;
    }

    // setters
    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/fresa.png");
        return imagen;
    }

    public void setPosition(int i, int j){
        this.pos[0] = i;
        this.pos[1] = j;
    }
}
