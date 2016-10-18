package co.edu.udea.compumovil.gr06.lab4fcm.Modelo;

/**
 * Created by jaime on 17/10/2016.
 */

public class Mensaje {

    private String mensaje, usuario, fecha;
    private String emisor;

    public Mensaje() {

    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
