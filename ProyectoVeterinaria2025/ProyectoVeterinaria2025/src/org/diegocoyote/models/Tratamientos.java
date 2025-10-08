
package org.diegocoyote.models;

import java.time.LocalDate;


/**
 *
 * @author juand
 */
public class Tratamientos {
    private int CodigoTratamiento;
    private String Descripcion;
    private LocalDate FechaInicio;
    private LocalDate FechaFin;
    private String MedicamentosIndicados;
    private int CodigoConsulta;

    public Tratamientos() {
    }

    public Tratamientos(int CodigoTratamiento, String Descripcion, LocalDate FechaInicio, LocalDate FechaFin, String MedicamentosIndicados, int CodigoConsulta) {
        this.CodigoTratamiento = CodigoTratamiento;
        this.Descripcion = Descripcion;
        this.FechaInicio = FechaInicio;
        this.FechaFin = FechaFin;
        this.MedicamentosIndicados = MedicamentosIndicados;
        this.CodigoConsulta = CodigoConsulta;
    }

    public int getCodigoTratamiento() {
        return CodigoTratamiento;
    }

    public void setCodigoTratamiento(int CodigoTratamiento) {
        this.CodigoTratamiento = CodigoTratamiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public LocalDate getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(LocalDate FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public LocalDate getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(LocalDate FechaFin) {
        this.FechaFin = FechaFin;
    }

    public String getMedicamentosIndicados() {
        return MedicamentosIndicados;
    }

    public void setMedicamentosIndicados(String MedicamentosIndicados) {
        this.MedicamentosIndicados = MedicamentosIndicados;
    }

    public int getCodigoConsulta() {
        return CodigoConsulta;
    }

    public void setCodigoConsulta(int CodigoConsulta) {
        this.CodigoConsulta = CodigoConsulta;
    }
    
    
}
