package com.example.universityclass.entidades;

public class AsesoriaPersonalizadaEntity {
    private boolean esAceptada=false;
    private String Fecha;
    private String FechaCompleta;
    private String idUsuario="";
    private String idProfesor="";
    private String nombreProfesor;
    private String apellidoProfesor="";
    private String nombreUsuario;
    private String descripcion;
    private String nombre;
    private String foto_url;
    private String key;
    private int rol_idrol;
    private  boolean esRespondida=false;

    public boolean isEsAceptada() {
        return esAceptada;
    }

    public void setEsAceptada(boolean esAceptada) {
        this.esAceptada = esAceptada;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getFechaCompleta() {
        return FechaCompleta;
    }

    public void setFechaCompleta(String fechaCompleta) {
        FechaCompleta = fechaCompleta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    public String getApellidoProfesor() {
        return apellidoProfesor;
    }

    public void setApellidoProfesor(String apellidoProfesor) {
        this.apellidoProfesor = apellidoProfesor;
    }

    public int getRol_idrol() {
        return rol_idrol;
    }

    public void setRol_idrol(int rol_idrol) {
        this.rol_idrol = rol_idrol;
    }

    public boolean isEsRespondida() {
        return esRespondida;
    }

    public void setEsRespondida(boolean esRespondida) {
        this.esRespondida = esRespondida;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
