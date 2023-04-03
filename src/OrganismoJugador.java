import javax.swing.ImageIcon;
import java.util.Random;

public class OrganismoJugador extends Organismo{
//    private boolean jugadorJugando = true;

    public OrganismoJugador(){
        edad = 1;
        energia = 50;
        vision = 1;
        // PRUEBA
        velocidad = 4;
        this.pos = new int[2];
    }

    public boolean atacar(Organismo organismoAComer) {
        if (energia == organismoAComer.energia && velocidad == organismoAComer.velocidad && edad == organismoAComer.edad){
            rnd = new Random();
            ganadorAleatorio = rnd.nextBoolean();
            // en caso de que el random tome boolean 0 quiere decir que el aleatorio no favoreció al OrganismoJugador
            if (!ganadorAleatorio) {
                /* Jugador pierde */
//                jugadorJugando = false;
                System.out.println("Jugador Pierde");
                return false;
            } else {
                this.energia += (organismoAComer.energia) / 2;
                this.vision += (organismoAComer.vision) / 2;
                this.velocidad += (organismoAComer.velocidad) / 2;
                return true;
            }
        } else if (comprobarAtaque(organismoAComer)) {
            this.energia += (organismoAComer.energia) / 2;
            this.vision += (organismoAComer.vision) / 2;
            this.velocidad += (organismoAComer.velocidad) / 2;
            return true;
        } else {
            /* Jugador pierde */
//            jugadorJugando = false;
            System.out.println("Jugador Pierde");
            return false;
        }
    }

    public boolean comprobarAtaque(Organismo organismoAComer){
        // evalua si organismo puede comer a organismoAComer
        if (energia > organismoAComer.energia || (energia == organismoAComer.energia && velocidad > organismoAComer.velocidad) || (energia == organismoAComer.energia && velocidad == organismoAComer.velocidad && edad > organismoAComer.edad)) {
            return true;
        } else {
            return false;
        }
    }

    public void atacar(AlimentoEnergia alimentoEnergia){
        this.energia += alimentoEnergia.getAtributo();
    }

    public void atacar(AlimentoVision alimentoVision){
        this.vision += alimentoVision.getAtributo();
    }

    public void atacar(AlimentoVelocidad alimentoVelocidad){
        this.velocidad += alimentoVelocidad.getAtributo();
    }

    public void aumentarEdad(){
        this.edad++;
    }

    public void disminuirEnergia(){
        this.energia--;
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

//    public boolean getJugadorJugando(){
//        return jugadorJugando;
//    }

    // setters
    public void setPosition(int i, int j){
        this.pos[0] = i;
        this.pos[1] = j;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/leon.png");
        return imagen;
    }

//    public void setJugadorJugando(boolean _valor){
//        this.jugadorJugando = _valor;
//    }
}
