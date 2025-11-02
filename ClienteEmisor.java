
public class ClienteEmisor extends Thread{
    private final int id;
    private final int numMensajes;
    private final BuzonEntrada buzon;

    public ClienteEmisor(int id, int numMensajes, BuzonEntrada buzon){
        this.id = id;
        this.numMensajes = numMensajes;
        this.buzon = buzon;
        setName("Cliente #"+id);
    }

    @Override
    public void run(){
        try{
            Mensaje mensajeInicial = new Mensaje(id, "INICIO");
            buzon.depositarMensaje(mensajeInicial);
            System.out.println(getName()+" ha enviado su mensaje inicial");

            for (int i = 0; i <= numMensajes; i++){
                boolean spam = Math.random() < 0.3;
                Mensaje m = new Mensaje(id, id+"-"+i, "NORMAL", spam);
                buzon.depositarMensaje(m);
                System.out.println(getName() + " enviado");

                Thread.sleep((int)(Math.random()*500)+500);
            }

            Mensaje mensajeFinal = new Mensaje(id, "FINAL");
            buzon.depositarMensaje(mensajeFinal);
            System.out.println(getName()+" ha enviado su mensaje final");
        } catch (InterruptedException e){
            System.err.println(getName()+" interrumpido");
        }
        
    }
}
