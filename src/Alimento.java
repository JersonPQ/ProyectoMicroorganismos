import java.awt.Color;

import javax.swing.ImageIcon;
public abstract class Alimento implements NPC{
    protected double tamano;
    protected Color color;

    public abstract int getAtributo();
    public abstract ImageIcon setImagen();
    public abstract String getInformacion();
}