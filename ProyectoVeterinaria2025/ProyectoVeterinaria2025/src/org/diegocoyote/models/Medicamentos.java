
package org.diegocoyote.models;

import java.time.LocalDate;


/**
 *
 * @author informatica
 */
public class Medicamentos {
    private int CodigoMedicamento;
    private String NombreMedicamento;
    private String Descripcion;
    private int Stock;
    private Double PrecioUnitario;
    private LocalDate FechaVencimiento;
    private int CodigoProveedor;

    public Medicamentos() {
    }

    public Medicamentos(int CodigoMedicamento, String NombreMedicamento, String Descripcion, int Stock, Double PrecioUnitario, LocalDate FechaVencimiento, int CodigoProveedor) {
        this.CodigoMedicamento = CodigoMedicamento;
        this.NombreMedicamento = NombreMedicamento;
        this.Descripcion = Descripcion;
        this.Stock = Stock;
        this.PrecioUnitario = PrecioUnitario;
        this.FechaVencimiento = FechaVencimiento;
        this.CodigoProveedor = CodigoProveedor;
    }

    public int getCodigoMedicamento() {
        return CodigoMedicamento;
    }

    public void setCodigoMedicamento(int CodigoMedicamento) {
        this.CodigoMedicamento = CodigoMedicamento;
    }

    public String getNombreMedicamento() {
        return NombreMedicamento;
    }

    public void setNombreMedicamento(String NombreMedicamento) {
        this.NombreMedicamento = NombreMedicamento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public Double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(Double PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }

    public LocalDate getFechaVencimiento() {
        return FechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate FechaVencimiento) {
        this.FechaVencimiento = FechaVencimiento;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }
    
    
}
