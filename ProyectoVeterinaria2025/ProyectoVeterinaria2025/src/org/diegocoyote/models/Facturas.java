
package org.diegocoyote.models;

import java.time.LocalDate;


/**
 *
 * @author informatica
 */
public class Facturas {
    private int CodigoFactura;
    private LocalDate FechaEmision;
    private Double Total;
    private String MetodoPago;
    private int CodigoCliente;
    private int CodigoEmpleado;

    public Facturas() {
    }

    public Facturas(int CodigoFactura, LocalDate FechaEmision, Double Total, String MetodoPago, int CodigoCliente, int CodigoEmpleado) {
        this.CodigoFactura = CodigoFactura;
        this.FechaEmision = FechaEmision;
        this.Total = Total;
        this.MetodoPago = MetodoPago;
        this.CodigoCliente = CodigoCliente;
        this.CodigoEmpleado = CodigoEmpleado;
    }

    public int getCodigoFactura() {
        return CodigoFactura;
    }

    public void setCodigoFactura(int CodigoFactura) {
        this.CodigoFactura = CodigoFactura;
    }

    public LocalDate getFechaEmision() {
        return FechaEmision;
    }

    public void setFechaEmision(LocalDate FechaEmision) {
        this.FechaEmision = FechaEmision;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public String getMetodoPago() {
        return MetodoPago;
    }

    public void setMetodoPago(String MetodoPago) {
        this.MetodoPago = MetodoPago;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    public int getCodigoEmpleado() {
        return CodigoEmpleado;
    }

    public void setCodigoEmpleado(int CodigoEmpleado) {
        this.CodigoEmpleado = CodigoEmpleado;
    }
    
    
}
