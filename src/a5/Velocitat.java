package a5;

public class Velocitat {

    /* Classe que porporciona una velocitat (int) aleatòria, per implementar l'exemple de Multicast:
     * ClientVelocimetre1.java
     * ClientVelocimetre2.java
     * SrvVelocitats.java
     */

    int vel,max;
    public Velocitat(int max) {
        this.max = max;
    }

    public int getVel() {
        return vel;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public int agafaVelocitat() {
        setVel((int)(Math.random()*max)+1);
        return getVel();
    }

}