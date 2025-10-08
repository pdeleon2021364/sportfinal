
package org.diegocoyote.models;

import java.time.LocalDate;


public class Citas {
    private int CodigoCita;
    private LocalDate FechaCita;
    private String HoraCita;
    private String Motivo;
    private String Estado;
    private int CodigoMascota;
    private int CodigoVeterinario;

    public Citas() {
    }

    public Citas(int CodigoCita, LocalDate FechaCita, String HoraCita, String Motivo, String Estado, int CodigoMascota, int CodigoVeterinario) {
        this.CodigoCita = CodigoCita;
        this.FechaCita = FechaCita;
        this.HoraCita = HoraCita;
        this.Motivo = Motivo;
        this.Estado = Estado;
        this.CodigoMascota = CodigoMascota;
        this.CodigoVeterinario = CodigoVeterinario;
    }

    public int getCodigoCita() {
        return CodigoCita;
    }

    public void setCodigoCita(int CodigoCita) {
        this.CodigoCita = CodigoCita;
    }

    public LocalDate getFechaCita() {
        return FechaCita;
    }

    public void setFechaCita(LocalDate FechaCita) {
        this.FechaCita = FechaCita;
    }

    public String getHoraCita() {
        return HoraCita;
    }

    public void setHoraCita(String HoraCita) {
        this.HoraCita = HoraCita;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String Motivo) {
        this.Motivo = Motivo;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public int getCodigoMascota() {
        return CodigoMascota;
    }

    public void setCodigoMascota(int CodigoMascota) {
        this.CodigoMascota = CodigoMascota;
    }

    public int getCodigoVeterinario() {
        return CodigoVeterinario;
    }

    public void setCodigoVeterinario(int CodigoVeterinario) {
        this.CodigoVeterinario = CodigoVeterinario;
    }
    
    
}
