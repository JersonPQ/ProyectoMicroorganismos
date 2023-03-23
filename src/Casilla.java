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

    public void setObjeto(Organismo _objeto){
        objeto = _objeto;
    }

    public void setObjeto(Alimento _objeto){
        objeto = _objeto;
    }   
}