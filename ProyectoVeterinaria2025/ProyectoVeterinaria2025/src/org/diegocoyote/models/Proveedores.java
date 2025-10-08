
package org.diegocoyote.models;

/**
 *
 * @author informatica
 */
public class Proveedores {
    private int CodigoProveedor;
    private String NombreProveedor;
    private String DireccionProveedor;
    private String TelefonoProveedor;
    private String CorreoProveedor;
    
    

    public Proveedores() {
    }

    public Proveedores(int CodigoProveedor, String NombreProveedor, String DireccionProveedor, String TelefonoProveedor, String CorreoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
        this.NombreProveedor = NombreProveedor;
        this.DireccionProveedor = DireccionProveedor;
        this.TelefonoProveedor = TelefonoProveedor;
        this.CorreoProveedor = CorreoProveedor;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }

    public String getNombreProveedor() {
        return NombreProveedor;
    }

    public void setNombreProveedor(String NombreProveedor) {
        this.NombreProveedor = NombreProveedor;
    }

    public String getDireccionProveedor() {
        return DireccionProveedor;
    }

    public void setDireccionProveedor(String DireccionProveedor) {
        this.DireccionProveedor = DireccionProveedor;
    }

    public String getTelefonoProveedor() {
        return TelefonoProveedor;
    }

    public void setTelefonoProveedor(String TelefonoProveedor) {
        this.TelefonoProveedor = TelefonoProveedor;
    }

    public String getCorreoProveedor() {
        return CorreoProveedor;
    }

    public void setCorreoProveedor(String CorreoProveedor) {
        this.CorreoProveedor = CorreoProveedor;
    }
    
    
    
}
