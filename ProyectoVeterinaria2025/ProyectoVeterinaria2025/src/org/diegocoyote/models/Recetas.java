
package org.diegocoyote.models;

/**
 *
 * @author informatica
 */
public class Recetas {
    private int CodigoReceta;
    private String Dosis;
    private String Frecuencia;
    private int DuracionDias;
    private String Indicaciones;
    private int CodigoConsulta;
    private int CodigoMedicamento;

    public Recetas() {
    }

    public Recetas(int CodigoReceta, String Dosis, String Frecuencia, int DuracionDias, String Indicaciones, int CodigoConsulta, int CodigoMedicamento) {
        this.CodigoReceta = CodigoReceta;
        this.Dosis = Dosis;
        this.Frecuencia = Frecuencia;
        this.DuracionDias = DuracionDias;
        this.Indicaciones = Indicaciones;
        this.CodigoConsulta = CodigoConsulta;
        this.CodigoMedicamento = CodigoMedicamento;
    }

    public int getCodigoReceta() {
        return CodigoReceta;
    }

    public void setCodigoReceta(int CodigoReceta) {
        this.CodigoReceta = CodigoReceta;
    }

    public String getDosis() {
        return Dosis;
    }

    public void setDosis(String Dosis) {
        this.Dosis = Dosis;
    }

    public String getFrecuencia() {
        return Frecuencia;
    }

    public void setFrecuencia(String Frecuencia) {
        this.Frecuencia = Frecuencia;
    }

    public int getDuracionDias() {
        return DuracionDias;
    }

    public void setDuracionDias(int DuracionDias) {
        this.DuracionDias = DuracionDias;
    }

    public String getIndicaciones() {
        return Indicaciones;
    }

    public void setIndicaciones(String Indicaciones) {
        this.Indicaciones = Indicaciones;
    }

    public int getCodigoConsulta() {
        return CodigoConsulta;
    }

    public void setCodigoConsulta(int CodigoConsulta) {
        this.CodigoConsulta = CodigoConsulta;
    }

    public int getCodigoMedicamento() {
        return CodigoMedicamento;
    }

    public void setCodigoMedicamento(int CodigoMedicamento) {
        this.CodigoMedicamento = CodigoMedicamento;
    }
    
    
}
