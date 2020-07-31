package com.example.universityclass.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Profesores implements Serializable {
    private String idusuario;
    private String correo;
    private String numeroDeCelular;
    private int saldo;
    private String codigo;
    private String nombre;
    private String apellido;
    private LocalDate fechaDeCumpleanos;
    private int estadoCuenta;
    private String contrasena;
    private int rol_idrol;
    private String universidad;
    private String foto_url;
    private String token;
    private String descripcion;
private String curso;
private String key;
    private AsesoriaPersonalizadaEntity[] solicitudes;


    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroDeCelular() {
        return numeroDeCelular;
    }

    public void setNumeroDeCelular(String numeroDeCelular) {
        this.numeroDeCelular = numeroDeCelular;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }



    public int getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(int estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getRol_idrol() {
        return rol_idrol;
    }

    public void setRol_idrol(int rol_idrol) {
        this.rol_idrol = rol_idrol;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    public LocalDate getFechaDeCumpleanos() {
        return fechaDeCumpleanos;
    }

    public void setFechaDeCumpleanos(LocalDate fechaDeCumpleanos) {
        this.fechaDeCumpleanos = fechaDeCumpleanos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AsesoriaPersonalizadaEntity[] getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(AsesoriaPersonalizadaEntity[] solicitudes) {
        this.solicitudes = solicitudes;
    }
}
