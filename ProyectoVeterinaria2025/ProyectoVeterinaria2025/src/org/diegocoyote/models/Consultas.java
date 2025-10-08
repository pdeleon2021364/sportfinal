
package org.diegocoyote.models;

import java.time.LocalDate;


/**
 *
 * @author juand
 */
public class Consultas {
    private int CodigoConsulta;
    private LocalDate FechaConsulta;
    private String Motivo;
    private String Diagnostico;
    private String Observaciones;
    private int CodigoMascota;
    private int CodigoVeterinario;

    public Consultas() {
    }

    public Consultas(int CodigoConsulta, LocalDate FechaConsulta, String Motivo, String Diagnostico, String Observaciones, int CodigoMascota, int CodigoVeterinario) {
        this.CodigoConsulta = CodigoConsulta;
        this.FechaConsulta = FechaConsulta;
        this.Motivo = Motivo;
        this.Diagnostico = Diagnostico;
        this.Observaciones = Observaciones;
        this.CodigoMascota = CodigoMascota;
        this.CodigoVeterinario = CodigoVeterinario;
    }

    public int getCodigoConsulta() {
        return CodigoConsulta;
    }

    public void setCodigoConsulta(int CodigoConsulta) {
        this.CodigoConsulta = CodigoConsulta;
    }

    public LocalDate getFechaConsulta() {
        return FechaConsulta;
    }

    public void setFechaConsulta(LocalDate FechaConsulta) {
        this.FechaConsulta = FechaConsulta;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String Motivo) {
        this.Motivo = Motivo;
    }

    public String getDiagnostico() {
        return Diagnostico;
    }

    public void setDiagnostico(String Diagnostico) {
        this.Diagnostico = Diagnostico;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
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
