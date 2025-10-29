
public class ClienteEmisor extends Thread{
    private final int id;
    private final int numMensajes;
    private final Buzon buzon;

    public ClienteEmisor(int id, int numMensajes, Buzon buzon){
        this.id = id;
        this.numMensajes = numMensajes;
        this.buzon = buzon;
    }

    @Override
    public void run(){
        try{
            Mensaje mensajeInicial = new Mensaje(id, "INICIO");
            buzon.depositarMensaje(mensajeInicial);

            for (int i = 0; i <= numMensajes; i++){
                boolean spam = Math.random() < 0.3;
                Mensaje m = new Mensaje(id, id+"-"+i, "NORMAL", spam);
                buzon.depositarMensaje(m);
            }
        } catch (InterruptedException e){
            System.err.println("interrumpido");
        }
        
    }
}
