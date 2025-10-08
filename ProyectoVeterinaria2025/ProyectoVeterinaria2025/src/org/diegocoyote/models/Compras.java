
package org.diegocoyote.models;

import java.time.LocalDate;


/**
 *
 * @author informatica
 */
public class Compras {
    private int CodigoCompra;
    private LocalDate FechaCompra;
    private Double Total;
    private String Detalle;
    private int CodigoProveedor;

    public Compras() {
    }

    public Compras(int CodigoCompra, LocalDate FechaCompra, Double Total, String Detalle, int CodigoProveedor) {
        this.CodigoCompra = CodigoCompra;
        this.FechaCompra = FechaCompra;
        this.Total = Total;
        this.Detalle = Detalle;
        this.CodigoProveedor = CodigoProveedor;
    }

    public int getCodigoCompra() {
        return CodigoCompra;
    }

    public void setCodigoCompra(int CodigoCompra) {
        this.CodigoCompra = CodigoCompra;
    }

    public LocalDate getFechaCompra() {
        return FechaCompra;
    }

    public void setFechaCompra(LocalDate FechaCompra) {
        this.FechaCompra = FechaCompra;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String Detalle) {
        this.Detalle = Detalle;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }
    
    
}
