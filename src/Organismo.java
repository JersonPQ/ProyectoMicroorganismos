import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Organismo implements NPC{
    protected int energia;
    protected int vision;
    protected int velocidad;
    protected int edad;
    protected int[] pos;
    protected Random rnd;
    protected boolean ganadorAleatorio;

    public Organismo(){
        rnd = new Random();
        edad = 1;
        energia = Configuracion.maxEnergia;
        vision = rnd.nextInt(3) + 1;
        velocidad = rnd.nextInt(3) + 1;
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
        return (this.energia > organismoAComer.energia || (this.energia == organismoAComer.energia && this.velocidad > organismoAComer.velocidad) || (this.energia == organismoAComer.energia && this.velocidad == organismoAComer.velocidad && this.edad > organismoAComer.edad));
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

    public void perderVision(){
        switch (this.edad){
            case 10:
                this.vision -= 1;
                break;
            case 20:
                this.vision -= 2;
                break;
            case 30:
                this.vision -= 3;
                break;
            case 35:
                this.vision -= 4;
                break;
            case 40:
                this.vision -= 5;
                break;
            case 45:
                this.vision -= 6;
                break;
            default:
                if (65 < this.edad){
                    this.vision -= 7;
                }
                break;
        }

        //Este es para validar que no baje de cero
        if(this.vision < 1){
            this.vision = 1;
        }
    }
}
