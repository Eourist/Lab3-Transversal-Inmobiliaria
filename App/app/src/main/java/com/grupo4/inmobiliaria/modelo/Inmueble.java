package com.grupo4.inmobiliaria.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {

    private int id;
    private String direccion;
    private String uso;
    private String tipo;
    private int ambientes;
    private int precio;
    private int propietarioId;
    private com.grupo4.inmobiliaria.modelo.Propietario propietario;
    private int visible;
    private String imagen;
    private int superficie;

    public Inmueble(int id, String direccion, String uso, String tipo, int ambientes, int precio, com.grupo4.inmobiliaria.modelo.Propietario propietario, int visible, String imagen, int superficie) {
        this.id = id;
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.precio = precio;
        this.propietario = propietario;
        this.visible = visible;
        this.imagen = imagen;
        this.superficie = superficie;
    }

    public Inmueble() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public com.grupo4.inmobiliaria.modelo.Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(com.grupo4.inmobiliaria.modelo.Propietario propietario) {
        this.propietario = propietario;
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public boolean getVisible() {
        return visible == 1 ? true : false;
    }

    public void setVisible(boolean visible) {
        this.visible = visible ? 1 : 0;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inmueble inmueble = (Inmueble) o;
        return id == inmueble.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
