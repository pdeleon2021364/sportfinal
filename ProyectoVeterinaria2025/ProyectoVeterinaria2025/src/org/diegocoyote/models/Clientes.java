
package org.diegocoyote.models;

import java.time.LocalDate;

public class Clientes {
    private int CodigoCliente;
    private String NombreCliente;
    private String ApellidoCliente;
    private String TelefonoCliente;
    private String DireccionCliente;
    private String EmailCliente;
    private LocalDate FechaRegistro;

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getApellidoCliente() {
        return ApellidoCliente;
    }

    public void setApellidoCliente(String ApellidoCliente) {
        this.ApellidoCliente = ApellidoCliente;
    }

    public String getTelefonoCliente() {
        return TelefonoCliente;
    }

    public void setTelefonoCliente(String TelefonoCliente) {
        this.TelefonoCliente = TelefonoCliente;
    }

    public String getDireccionCliente() {
        return DireccionCliente;
    }

    public void setDireccionCliente(String DireccionCliente) {
        this.DireccionCliente = DireccionCliente;
    }

    public String getEmailCliente() {
        return EmailCliente;
    }

    public void setEmailCliente(String EmailCliente) {
        this.EmailCliente = EmailCliente;
    }

    public LocalDate getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(LocalDate FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public Clientes() {
    }

    public Clientes(int CodigoCliente, String NombreCliente, String ApellidoCliente, String TelefonoCliente, String DireccionCliente, String EmailCliente, LocalDate FechaRegistro) {
        this.CodigoCliente = CodigoCliente;
        this.NombreCliente = NombreCliente;
        this.ApellidoCliente = ApellidoCliente;
        this.TelefonoCliente = TelefonoCliente;
        this.DireccionCliente = DireccionCliente;
        this.EmailCliente = EmailCliente;
        this.FechaRegistro = FechaRegistro;
    }

    
}
