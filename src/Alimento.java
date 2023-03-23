import java.awt.Color;

import javax.swing.ImageIcon;
public abstract class Alimento{
    protected double tamano;
    protected Color color;

    public abstract int getAtributo();
    public abstract ImageIcon setImagen();
}