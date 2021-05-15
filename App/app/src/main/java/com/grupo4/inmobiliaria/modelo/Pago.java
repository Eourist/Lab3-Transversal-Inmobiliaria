package com.grupo4.inmobiliaria.modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id;
    //private int numero;
    private int contratoId;
    private Contrato contrato;
    private int importe;
    private String fecha;

    public Pago() {}

    public Pago(int id, Contrato contrato, int importe, String fecha) {
        this.id = id;
        this.contrato = contrato;
        this.importe = importe;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
