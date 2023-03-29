import java.awt.Color;

import javax.swing.ImageIcon;
public abstract class Alimento implements NPC{
    protected double tamano;
    protected Color color;
    protected int[] pos;

    public abstract int getAtributo();
    public abstract ImageIcon setImagen();
    public abstract String getInformacion();
    public abstract int[] getPosition();
    public abstract void setPosition(int i, int j);
}