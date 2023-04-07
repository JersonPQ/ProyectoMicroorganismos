import javax.swing.ImageIcon;

public interface NPC {
    String getInformacion();
    ImageIcon setImagen();
    int[] getPosition();
    void setPosition(int i, int j);
}