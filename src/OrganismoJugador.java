import javax.swing.ImageIcon;

public class OrganismoJugador extends Organismo{
    public OrganismoJugador(){
        energia = 1;
        vision = 1;
        velocidad = 1;
        edad = 1;
    }

    public void setPosition(int i, int j){
        this.pos = new int[2];
        this.pos[0] = i;
        this.pos[1] = j;
    }

    public void moverse() {
        /* CODE */
    }

    public void atacar(Organismo organismoAComer) {
        if (energia == organismoAComer.energia && velocidad == organismoAComer.velocidad && edad == organismoAComer.edad){
            ganadorAleatorio = rnd.nextBoolean();
            // en caso de que el random tome boolean 0 quiere decir que el aleatorio no favoreció al OrganismoJugador
            if (!ganadorAleatorio) {
                /* Jugador pierde */
            } else {
                this.energia += (organismoAComer.energia) / 2;
                this.vision += (organismoAComer.vision) / 2;
                this.velocidad += (organismoAComer.velocidad) / 2;
            }
        } else if (energia > organismoAComer.energia || (energia == organismoAComer.energia && velocidad > organismoAComer.velocidad) || (energia == organismoAComer.energia && velocidad == organismoAComer.velocidad && edad > organismoAComer.edad)) {
            this.energia += (organismoAComer.energia) / 2;
            this.vision += (organismoAComer.vision) / 2;
            this.velocidad += (organismoAComer.velocidad) / 2;
        } else {
            /* Jugador pierde */
        }
    }

    public void atacar(AlimentoEnergia alimentoEnergia){
        this.energia += alimentoEnergia.getAtributo();
    }

    public void atacar(AlimentoVision alimentoVision){
        this.velocidad += alimentoVision.getAtributo();
    }

    public void atacar(AlimentoVelocidad alimentoVelocidad){
        this.velocidad += alimentoVelocidad.getAtributo();
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("leon.png");
        return imagen;
    }
}
