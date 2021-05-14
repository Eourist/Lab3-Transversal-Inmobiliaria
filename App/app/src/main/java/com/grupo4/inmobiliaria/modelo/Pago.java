package com.grupo4.inmobiliaria.modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int Id;
    //private int numero;
    private int contratoId;
    private Contrato contrato;
    private int importe;
    private String fecha;

    public Pago() {}

    public Pago(int Id, int numero, Contrato contrato, int importe, String Fecha) {
        this.Id = Id;
        //this.numero = numero;
        this.contrato = contrato;
        this.importe = importe;
        this.fecha = Fecha;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
    }

    /*public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }*/

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getImporte() {
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
