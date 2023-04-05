import javax.swing.ImageIcon;
import java.util.Random;

public class OrganismoJugador extends Organismo{
//    private boolean jugadorJugando = true;

    public OrganismoJugador(){
        edad = 1;
        energia = 50;
        vision = 5;
        // PRUEBA
        velocidad = 4;
        this.pos = new int[2];
    }

    public boolean atacar(Organismo organismoAComer) {
        boolean ganador;
        if (energia == organismoAComer.energia && velocidad == organismoAComer.velocidad && edad == organismoAComer.edad){
            rnd = new Random();
            ganadorAleatorio = rnd.nextBoolean();
            // en caso de que el random tome boolean 0 quiere decir que el aleatorio no favoreció al OrganismoJugador
            if (!ganadorAleatorio) {
                /* Jugador pierde */
//                jugadorJugando = false;
                System.out.println("Jugador Pierde");
                ganador = false;
            } else {
                this.energia += (organismoAComer.energia) / 2;
                this.vision += (organismoAComer.vision) / 2;
                this.velocidad += (organismoAComer.velocidad) / 2;
                ganador =  true;
            }
        } else if (comprobarAtaque(organismoAComer)) {
            this.energia += (organismoAComer.energia) / 2;
            this.vision += (organismoAComer.vision) / 2;
            this.velocidad += (organismoAComer.velocidad) / 2;
            ganador = true;
        } else {
            /* Jugador pierde */
//            jugadorJugando = false;
            System.out.println("Jugador Pierde");
            ganador = false;
        }

        //Aqui se valida que no se haya pasado del tope de maximo de stats
        if(ganador){
            if(this.energia > Configuracion.maxEnergia){
                this.energia = Configuracion.maxEnergia;
            }
            if(this.vision > Configuracion.maxVision){
                this.vision = Configuracion.maxVision;
            }
            if(this.velocidad > Configuracion.maxVelocidad){
                this.velocidad = Configuracion.maxVelocidad;
            }
        }
        return ganador;
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
        if(this.energia + alimentoEnergia.getAtributo() > Configuracion.maxEnergia){
            this.energia = Configuracion.maxEnergia;
        }
        else{
            this.energia += alimentoEnergia.getAtributo();
        }
        // si velocidad es mayor que 1 entra, caso contrario no resta para mantener un minimo de velocidad de 1
        if (velocidad > 1){
            // al comer energia le resta velocidad
            this.velocidad--;
        }
    }

    public void atacar(AlimentoVision alimentoVision){
        if(this.vision + alimentoVision.getAtributo()  > Configuracion.maxVision){
            this.vision = Configuracion.maxVision;
        }
        else{
            this.vision += alimentoVision.getAtributo();
        }
    }

    public void atacar(AlimentoVelocidad alimentoVelocidad){
        if(this.velocidad + alimentoVelocidad.getAtributo() > Configuracion.maxVelocidad){
            this.velocidad = Configuracion.maxVelocidad;
        }
        else{
            this.velocidad += alimentoVelocidad.getAtributo();
        }
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

    // setters
    public void setPosition(int i, int j){
        this.pos[0] = i;
        this.pos[1] = j;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/leon.png");
        return imagen;
    }

    public void perderVision(){
        if( 5 < this.edad && this.edad < 10){
            this.vision -= 1;
        }
        if( 10 < this.edad && this.edad < 15){
            this.vision -= 2;
        }
        if( 20 < this.edad && this.edad < 25){
            this.vision -= 3;
        }
        if( 30 < this.edad && this.edad < 35){
            this.vision -= 4;
        }
        if( 35 < this.edad && this.edad < 40){
            this.vision -= 5;
        }
        if( 40 < this.edad){
            this.vision -= 6;
        }
    }
}
