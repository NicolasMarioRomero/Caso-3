import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {
    private final Queue<Mensaje> cola = new LinkedList<>();
    private final int capacidad;

    public BuzonEntrada(int capacidad){
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
