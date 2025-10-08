
package org.diegocoyote.models;

/**
 *
 * @author juand
 */
public class Vacunas {
    private int CodigoVacuna;
    private String NombreVacuna;
    private String Descripcion;
    private String Dosis;
    private int FrecuenciaMeses;

    public Vacunas() {
    }

    public Vacunas(int CodigoVacuna, String NombreVacuna, String Descripcion, String Dosis, int FrecuenciaMeses) {
        this.CodigoVacuna = CodigoVacuna;
        this.NombreVacuna = NombreVacuna;
        this.Descripcion = Descripcion;
        this.Dosis = Dosis;
        this.FrecuenciaMeses = FrecuenciaMeses;
    }

    public int getCodigoVacuna() {
        return CodigoVacuna;
    }

    public void setCodigoVacuna(int CodigoVacuna) {
        this.CodigoVacuna = CodigoVacuna;
    }

    public String getNombreVacuna() {
        return NombreVacuna;
    }

    public void setNombreVacuna(String NombreVacuna) {
        this.NombreVacuna = NombreVacuna;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getDosis() {
        return Dosis;
    }

    public void setDosis(String Dosis) {
        this.Dosis = Dosis;
    }

    public int getFrecuenciaMeses() {
        return FrecuenciaMeses;
    }

    public void setFrecuenciaMeses(int FrecuenciaMeses) {
        this.FrecuenciaMeses = FrecuenciaMeses;
    }
    
    
}
