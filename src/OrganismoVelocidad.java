import javax.swing.ImageIcon;

public class OrganismoVelocidad extends Organismo{

    public OrganismoVelocidad(){
        energia = 2;
        // PRUEBA
        vision = 3;
        velocidad = 4;
        edad = 1;
    }

    public void atacar(Organismo organismoAComer) {
        if (energia == organismoAComer.energia && velocidad == organismoAComer.velocidad && edad == organismoAComer.edad){
            ganadorAleatorio = rnd.nextBoolean();
            // en caso de que el random tome boolean 0 quiere decir que el aleatorio no favoreció al Organismo que va a comer
            if (!ganadorAleatorio) {
                organismoAComer.energia += energia/2;
                organismoAComer.velocidad += velocidad/2;
                organismoAComer.vision += vision/2;
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
            // organismo que iba a ser comido toma los atributos
            organismoAComer.energia += energia/2;
            organismoAComer.velocidad += velocidad/2;
            organismoAComer.vision += vision/2;
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

    // getters
    public int[] getPosicion(){
        return this.pos;
    }

    public String getInformacion(){
        return "Organismo Velocidad\n" + 
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
        ImageIcon imagen = new ImageIcon("images/tortuga-marina.png");
        return imagen;
    }

}
