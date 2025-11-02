import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private final Queue<Mensaje> cola = new LinkedList<>();
    private final Integer capacidad;

    public Buzon(Integer capacidad){
        this.capacidad = capacidad;
    }

    public synchronized void depositarMensaje(Mensaje m) throws InterruptedException{
        if (capacidad != null){
            while(cola.size() >= capacidad){
                wait();
            }
        }  
        cola.add(m);
        notifyAll();
    }

    public synchronized Mensaje retirar() throws InterruptedException{
        while (cola.isEmpty()){
            wait();
        }
        Mensaje m = cola.poll();
        notifyAll();
        return m;
    }
}
