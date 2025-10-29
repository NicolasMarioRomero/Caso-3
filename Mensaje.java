public class Mensaje{
    public final String tipo;
    private final int idCliente;
    private final String idMensaje;
    private final boolean esSpam;
    private int tiempoCuarentena;

    public Mensaje(int idC, String idM, String t, boolean s){
        this.idCliente = idC;
        this.idMensaje = idM;
        this.tipo = t;
        this.esSpam = s;
    }

    public Mensaje(int idCliente, String tipo) {
        this(idCliente, tipo, tipo, false);
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean esSpam() {
        return esSpam;
    }

    public int getTiempoCuarentena() {
        return tiempoCuarentena;
    }

    public void setTiempoCuarentena(int tiempoCuarentena) {
        this.tiempoCuarentena = tiempoCuarentena;
    }
}