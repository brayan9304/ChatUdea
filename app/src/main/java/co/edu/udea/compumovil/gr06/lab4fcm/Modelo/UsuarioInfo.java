package co.edu.udea.compumovil.gr06.lab4fcm.Modelo;

/**
 * Created by jaime on 17/10/2016.
 */

public class UsuarioInfo {

    public static final int ESTADO_CONECTADO = 0;
    public static final int ESTADO_DESCONECTADO = 1;

    private String nombre;
    private int estado;

    public UsuarioInfo(String nombre, int estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
