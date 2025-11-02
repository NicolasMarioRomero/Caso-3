public class FiltroSpam extends Thread{
    private final Buzon buzonEntrada;
    private final Buzon buzonCuarentena;
    private final Buzon buzonEntrega;
    private int clientesFinalizados = 0;
    private final int totalClientes;

    public FiltroSpam(Buzon buzonEntrada, Buzon buzonCuarentena, Buzon buzonEntrega, int totalClientes, int id){
        this.buzonEntrada = buzonEntrada;
        this.buzonCuarentena = buzonCuarentena;
        this.buzonEntrega = buzonEntrega;
        this.totalClientes = totalClientes;
        setName("FiltroSpam-" + id);
    }

    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void run(){
        try {
            while (true) { 
                Mensaje m = buzonEntrada.retirar();
                if (m.getTipo().equals("INICIO")) {
                    System.out.println(getName() + " detectó inicio de cliente " + m.getIdCliente());
                }
                else if (m.getTipo().equals("FINAL")){
                    clientesFinalizados++;
                    System.out.println(getName() + " detectó fin de cliente " + m.getIdCliente());
                    if (clientesFinalizados == totalClientes) {
                        buzonEntrega.depositarMensaje(new Mensaje(0, "FINAL"));
                        buzonCuarentena.depositarMensaje(new Mensaje(0, "FINAL"));
                        System.out.println(getName() + " envió mensajes de FIN a entrega y cuarentena");
                        break;
                    }
                }
                else{
                    if (m.esSpam()){
                        int tiempo = (int)(Math.random()*10000)+10000;
                        m.setTiempoCuarentena(tiempo);
                        buzonCuarentena.depositarMensaje(m);
                        System.out.println(getName() + " envió mensaje SPAM de " + m.getIdCliente() + " a cuarentena (" + tiempo + "ms)");
                    }
                    else{
                        buzonEntrega.depositarMensaje(m);
                        System.out.println(getName() + " envió mensaje válido de " + m.getIdCliente());
                    }
                }
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            System.err.println(getName() + " interrumpido");
        }
    }
}
