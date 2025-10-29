import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private final Queue<Mensaje> cola = new LinkedList<>();
    private final int capacidad;

    public Buzon(int capacidad){
        this.capacidad = capacidad;
    }

    public synchronized void depositarMensaje(Mensaje m) throws InterruptedException{
        while(cola.size() >= capacidad){
            wait();
        }

        cola.add(m);
        notifyAll();
    }
}
