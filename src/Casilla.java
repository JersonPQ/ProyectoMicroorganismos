import javax.swing.JButton;

public class Casilla {
    private Object objeto;
    public JButton boton;

    public Casilla(){
        objeto = null;
        boton = new JButton();
        boton.setEnabled(false);
    }

    public Object getObjeto(){
        return objeto;
    }
}
