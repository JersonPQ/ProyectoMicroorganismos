import javax.swing.ImageIcon;

public class OrganismoJugador extends Organismo{
    public OrganismoJugador(){
        energia = 1;
        vision = 1;
        // PRUEBA
        velocidad = 3;
        edad = 1;
    }

    public void atacar(Organismo organismoAComer) {
        if (energia == organismoAComer.energia && velocidad == organismoAComer.velocidad && edad == organismoAComer.edad){
            ganadorAleatorio = rnd.nextBoolean();
            // en caso de que el random tome boolean 0 quiere decir que el aleatorio no favoreció al OrganismoJugador
            if (!ganadorAleatorio) {
                /* Jugador pierde */
                System.out.println("Jugador Pierde");
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
            System.out.println("Jugador Pierde");
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

    // getters
    public int[] getPosicion(){
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
        this.pos = new int[2];
        this.pos[0] = i;
        this.pos[1] = j;
    }

    public ImageIcon setImagen(){
        ImageIcon imagen = new ImageIcon("images/leon.png");
        return imagen;
    }
}
