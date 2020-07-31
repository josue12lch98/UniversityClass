package com.example.universityclass.entidades;

public class AsesoriaGrupal {

    private String Fecha;
    private String FechaCompleta;
    private String idUsuario;
    private String idProfesor;
    private String nombreProfesor;
    private String nombreUsuario;
    private String descripcion;


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
}
