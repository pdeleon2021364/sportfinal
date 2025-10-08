
package org.diegocoyote.models;

import java.time.LocalDate;


/**
 *
 * @author juand
 */
public class Vacunaciones {
    private int CodigoVacunacion;
    private LocalDate FechaAplicacion;
    private String Observaciones;
    private int CodigoMascota;
    private int CodigoVacuna;
    private int CodigoVeterinario;

    public Vacunaciones() {
    }

    public Vacunaciones(int CodigoVacunacion, LocalDate FechaAplicacion, String Observaciones, int CodigoMascota, int CodigoVacuna, int CodigoVeterinario) {
        this.CodigoVacunacion = CodigoVacunacion;
        this.FechaAplicacion = FechaAplicacion;
        this.Observaciones = Observaciones;
        this.CodigoMascota = CodigoMascota;
        this.CodigoVacuna = CodigoVacuna;
        this.CodigoVeterinario = CodigoVeterinario;
    }

    public int getCodigoVacunacion() {
        return CodigoVacunacion;
    }

    public void setCodigoVacunacion(int CodigoVacunacion) {
        this.CodigoVacunacion = CodigoVacunacion;
    }

    public LocalDate getFechaAplicacion() {
        return FechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate FechaAplicacion) {
        this.FechaAplicacion = FechaAplicacion;
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

    public int getCodigoVacuna() {
        return CodigoVacuna;
    }

    public void setCodigoVacuna(int CodigoVacuna) {
        this.CodigoVacuna = CodigoVacuna;
    }

    public int getCodigoVeterinario() {
        return CodigoVeterinario;
    }

    public void setCodigoVeterinario(int CodigoVeterinario) {
        this.CodigoVeterinario = CodigoVeterinario;
    }
    
    
}
