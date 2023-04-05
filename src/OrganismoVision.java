import javax.swing.ImageIcon;
import java.util.Random;

public class OrganismoVision extends Organismo{

    public OrganismoVision(){
        edad = 1;
        energia = 15;
        vision = 5;
        // PRUEBA
        velocidad = 7;
        this.pos = new int[2];
    }

    public boolean atacar(Organismo organismoAComer) {
        boolean ganador;
        if (this.energia == organismoAComer.energia && this.velocidad == organismoAComer.velocidad && this.edad == organismoAComer.edad){
            rnd = new Random();
            ganadorAleatorio = rnd.nextBoolean();
            // en caso de que el random tome boolean 0 quiere decir que el aleatorio no favoreció al Organismo que va a comer
            if (!ganadorAleatorio) {
                organismoAComer.energia += energia / 2;
                organismoAComer.velocidad += velocidad / 2;
                organismoAComer.vision += vision / 2;
                ganador =  false;
            } else {
                this.energia += (organismoAComer.energia) / 2;
                this.vision += (organismoAComer.vision) / 2;
                this.velocidad += (organismoAComer.velocidad) / 2;
                ganador = true;
            }
        } else if (comprobarAtaque(organismoAComer)) {
            this.energia += (organismoAComer.energia) / 2;
            this.vision += (organismoAComer.vision) / 2;
            this.velocidad += (organismoAComer.velocidad) / 2;
            ganador = true;
        } else {
            // organismo que iba a ser comido toma los atributos
            organismoAComer.energia += energia / 2;
            organismoAComer.velocidad += velocidad / 2;
            organismoAComer.vision += vision / 2;
            ganador = false;
        }
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
        else{
            if(organismoAComer.energia > Configuracion.maxEnergia){
                organismoAComer.energia = Configuracion.maxEnergia;
            }
            if(organismoAComer.vision > Configuracion.maxVision){
                organismoAComer.vision = Configuracion.maxVision;
            }
            if(organismoAComer.velocidad > Configuracion.maxVelocidad){
                organismoAComer.velocidad = Configuracion.maxVelocidad;
            }
        }
        return ganador;
    }

    public boolean comprobarAtaque(Organismo organismoAComer){
        // evalua si organismo puede comer a organismoAComer
        if (this.energia > organismoAComer.energia || (this.energia == organismoAComer.energia && this.velocidad > organismoAComer.velocidad) || (this.energia == organismoAComer.energia && this.velocidad == organismoAComer.velocidad && this.edad > organismoAComer.edad)) {
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
        return "Organismo Vision\n" + 
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
        ImageIcon imagen = new ImageIcon("images/loro.png");
        return imagen;
    }

    public void perderVision(){
        if( 5 < this.edad && this.edad <= 10){
            this.vision -= 1;
        } else if ( 10 < this.edad && this.edad <= 20){
            this.vision -= 2;
        } else if ( 20 < this.edad && this.edad <= 30){
            this.vision -= 3;
        } else if ( 30 < this.edad && this.edad <= 35){
            this.vision -= 4;
        } else if ( 35 < this.edad && this.edad <= 40){
            this.vision -= 5;
        } else if ( 40 < this.edad){
            this.vision -= 6;
        }
        //Este es para validar que no baje de cero
        if(this.edad < 1){
            this.edad = 1;
        }
    }
}
