package com.grupo4.inmobiliaria.modelo;

import java.io.Serializable;

public class Inquilino implements Serializable {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String lugarTrabajo;
    private String email;
    private String telefono;
    private String nombreGarante;
    private String apellidoGarante;
    private String dniGarante;
    private String emailGarante;
    private String telefonoGarante;

    public Inquilino() {}

    public Inquilino(int Id, String dni, String nombre, String apellido, String lugarTrabajo, String email, String telefono, String nombreGarante, String apellidoGarante, String dniGarante, String emailGarante, String telefonoGarante) {
        this.id = Id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.lugarTrabajo = lugarTrabajo;
        this.email = email;
        this.telefono = telefono;
        this.nombreGarante = nombreGarante;
        this.apellidoGarante = apellidoGarante;
        this.dniGarante = dniGarante;
        this.emailGarante = emailGarante;
        this.telefonoGarante = telefonoGarante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreGarante() {
        return nombreGarante;
    }

    public void setNombreGarante(String nombreGarante) {
        this.nombreGarante = nombreGarante;
    }

    public String getTelefonoGarante() {
        return telefonoGarante;
    }

    public void setTelefonoGarante(String telefonoGarante) {
        this.telefonoGarante = telefonoGarante;
    }

    public String getApellidoGarante() {
        return apellidoGarante;
    }

    public void setApellidoGarante(String apellidoGarante) {
        this.apellidoGarante = apellidoGarante;
    }

    public String getDniGarante() {
        return dniGarante;
    }

    public void setDniGarante(String dniGarante) {
        this.dniGarante = dniGarante;
    }

    public String getEmailGarante() {
        return emailGarante;
    }

    public void setEmailGarante(String emailGarante) {
        this.emailGarante = emailGarante;
    }
}
