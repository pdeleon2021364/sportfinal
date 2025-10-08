package org.diegocoyote.models;

import java.time.LocalDate;

/**
 *
 * @author juand
 */
public class Veterinarios {
    private int CodigoVeterinario;
    private String NombreVeterinario;
    private String ApellidoVeterinario;
    private String Especialidad;
    private String TelefonoVeterinario;
    private String CorreoVeterinario;
    private LocalDate FechaIngreso;
    private String Estado;

    public Veterinarios() {
    }

    public Veterinarios(int CodigoVeterinario, String NombreVeterinario, String ApellidoVeterinario, String Especialidad, String TelefonoVeterinario, String CorreoVeterinario, LocalDate FechaIngreso, String Estado) {
        this.CodigoVeterinario = CodigoVeterinario;
        this.NombreVeterinario = NombreVeterinario;
        this.ApellidoVeterinario = ApellidoVeterinario;
        this.Especialidad = Especialidad;
        this.TelefonoVeterinario = TelefonoVeterinario;
        this.CorreoVeterinario = CorreoVeterinario;
        this.FechaIngreso = FechaIngreso;
        this.Estado = Estado;
    }

    public int getCodigoVeterinario() {
        return CodigoVeterinario;
    }

    public void setCodigoVeterinario(int CodigoVeterinario) {
        this.CodigoVeterinario = CodigoVeterinario;
    }

    public String getNombreVeterinario() {
        return NombreVeterinario;
    }

    public void setNombreVeterinario(String NombreVeterinario) {
        this.NombreVeterinario = NombreVeterinario;
    }

    public String getApellidoVeterinario() {
        return ApellidoVeterinario;
    }

    public void setApellidoVeterinario(String ApellidoVeterinario) {
        this.ApellidoVeterinario = ApellidoVeterinario;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String Especialidad) {
        this.Especialidad = Especialidad;
    }

    public String getTelefonoVeterinario() {
        return TelefonoVeterinario;
    }

    public void setTelefonoVeterinario(String TelefonoVeterinario) {
        this.TelefonoVeterinario = TelefonoVeterinario;
    }

    public String getCorreoVeterinario() {
        return CorreoVeterinario;
    }

    public void setCorreoVeterinario(String CorreoVeterinario) {
        this.CorreoVeterinario = CorreoVeterinario;
    }

    public LocalDate getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(LocalDate FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    
    
}
