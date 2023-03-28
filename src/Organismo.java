import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Organismo implements NPC{
    protected int energia = 1;
    protected int vision = 1;
    protected int velocidad = 1;
    protected int edad = 1;
    protected int[] pos;
    protected Random rnd;
    protected boolean ganadorAleatorio;

    public abstract void setPosition(int i, int j);
    public abstract boolean atacar(Organismo organismoAComer);
    public abstract void atacar(AlimentoEnergia alimentoEnergia);
    public abstract void atacar(AlimentoVelocidad alimentoVelocidad);
    public abstract void atacar(AlimentoVision alimentoVision);
    public abstract boolean comprobarAtaque(Organismo organismoAComer);
    public abstract ImageIcon setImagen();
    public abstract int[] getPosicion();
    public abstract String getInformacion();
}
