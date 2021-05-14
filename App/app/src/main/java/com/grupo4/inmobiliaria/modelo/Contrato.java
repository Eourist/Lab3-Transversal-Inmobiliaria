package com.grupo4.inmobiliaria.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Contrato implements Serializable {

    private int id;
    private String fechaDesde;
    private String fechaHasta;
    private int precio;
    private int inquilinoId;
    private Inquilino inquilino;
    private int inmuebleId;
    private Inmueble inmueble;
    private int estado;
    private String nombreEstado;

    public Contrato() {}
    public Contrato(int Id, String FechaDesde, String FechaHasta, int Precio, Inquilino inquilino, Inmueble inmueble) {
        this.id = Id;
        this.fechaDesde = FechaDesde;
        this.fechaHasta = FechaHasta;
        this.precio = Precio;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getInquilinoId() {
        return inquilinoId;
    }

    public void setInquilinoId(int inquilinoId) {
        this.inquilinoId = inquilinoId;
    }

    public int getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(int inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
