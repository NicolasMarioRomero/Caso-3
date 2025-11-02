public class ServidorEntrega extends Thread {
    private final Buzon buzonEntrega;

    public ServidorEntrega(Buzon buzonEntrega, int id) {
        this.buzonEntrega = buzonEntrega;
        setName("ServidorEntrega-" + id);
    }

    @Override
    public void run(){
        System.out.println(getName() + " iniciado.");

        boolean activo = false;
        boolean terminado = false;
        try {
            while(!terminado){
                Mensaje m = buzonEntrega.retirar();
                if (m != null){
                    switch (m.getTipo()){
                        case "INICIO" -> {
                            activo = true;
                            System.out.println(getName() + " recibió mensaje INICIO del cliente " + m.getIdCliente());
                        }

                        case "NORMAL" ->{
                            if (activo){
                                System.out.println(getName() + " procesando mensaje " + m.getIdMensaje());
                                Thread.sleep((int) (Math.random() * 500) + 500);
                            }
                            break;
                        }
                        case "FINAL" ->{
                            System.out.println(getName() + " recibió mensaje FINAL. Terminando ejecución...");
                            terminado = true;
                            break;
                        }
                        default -> {
                            System.out.println(getName() + " recibió mensaje desconocido: " + m.getTipo());
                            break;
                        }
                    }
                    Thread.yield();
                }
                System.out.println(getName() + " ha finalizado correctamente.");
            }
        } catch (InterruptedException e) {
            System.err.println(getName() + " interrumpido.");
        }
    }
}
