import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Organismo {
    protected int energia = 1;
    protected int vision = 1;
    protected int velocidad = 1;
    protected int edad = 1;
    protected int[] pos;
    protected Random rnd;
    protected boolean ganadorAleatorio;

    public abstract void setPosition(int i, int j);
    public abstract void moverse();
    public abstract void atacar(Organismo organismoAComer);
    public abstract void atacar(AlimentoEnergia alimentoEnergia);
    public abstract void atacar(AlimentoVelocidad alimentoVelocidad);
    public abstract void atacar(AlimentoVision alimentoVision);
    public abstract ImageIcon setImagen();
}
