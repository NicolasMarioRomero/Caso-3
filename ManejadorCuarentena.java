import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ManejadorCuarentena extends Thread{
    private final Buzon buzonCuarentena;
    private final Buzon buzonEntrega;
    private final Queue<Mensaje> cuarentenaActiva = new LinkedList<>();
    private boolean terminado = false;

    public ManejadorCuarentena(Buzon buzonCuarentena, Buzon buzonEntrega) {
        this.buzonCuarentena = buzonCuarentena;
        this.buzonEntrega = buzonEntrega;
        setName("ManejadorCuarentena");
    }

    @Override
    public void run(){
        System.out.println(getName() + " iniciado");

        try {
            while(!terminado){
                while (true) { 
                    Mensaje m = null;
                    synchronized (buzonCuarentena) {
                        if (buzonCuarentenaVacio()) break;
                        m = buzonCuarentena.retirar();
                    }

                    if (m != null){
                        if (m.getTipo().equals("FINAL")){
                            terminado = true;
                            System.out.println(getName() + " recibió mensaje FINAL");
                            break;
                        } else{
                            cuarentenaActiva.add(m);
                            System.out.println(getName() + " agregó mensaje a cuarentena: " + m.getIdMensaje());
                        }
                    }
                }

                if (!cuarentenaActiva.isEmpty()){
                    Iterator<Mensaje> it = cuarentenaActiva.iterator();
                    while (it.hasNext()){
                        Mensaje m = it.next();

                        m.setTiempoCuarentena(m.getTiempoCuarentena()-1000);

                        if (m.getTiempoCuarentena() <= 0){
                            int num = (int) (Math.random()*21)+1;
                            if (num % 7 == 0) {
                                System.out.println(getName() + " DESCARTÓ mensaje malicioso " + m.getIdMensaje());
                            } else {
                                buzonEntrega.depositarMensaje(m);
                                System.out.println(getName() + " liberó mensaje válido " + m.getIdMensaje());
                            }
                            it.remove();
                        }
                    }
                }
                Thread.sleep(1000);
            }

            buzonEntrega.depositarMensaje(new Mensaje(0, "FINAL"));
            System.out.println(getName() + " envió mensaje FINAL a buzón de entrega y terminó.");
        } catch (InterruptedException e) {
            System.err.println(getName() + " interrumpido.");
        }
    }

    private boolean buzonCuarentenaVacio() {
        try {
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
