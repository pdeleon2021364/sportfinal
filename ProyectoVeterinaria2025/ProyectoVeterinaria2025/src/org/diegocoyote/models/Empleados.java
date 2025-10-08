
package org.diegocoyote.models;

/**
 *
 * @author juand
 */
public class Empleados {
    private int CodigoEmpleado;
    private String NombreEmpleado;
    private String ApellidoEmpleado;
    private String Cargo;
    private String TelefonoEmpleado;
    private String CorreoEmpleado;

    public Empleados() {
    }

    public Empleados(int CodigoEmpleado, String NombreEmpleado, String ApellidoEmpleado, String Cargo, String TelefonoEmpleado, String CorreoEmpleado) {
        this.CodigoEmpleado = CodigoEmpleado;
        this.NombreEmpleado = NombreEmpleado;
        this.ApellidoEmpleado = ApellidoEmpleado;
        this.Cargo = Cargo;
        this.TelefonoEmpleado = TelefonoEmpleado;
        this.CorreoEmpleado = CorreoEmpleado;
    }

    public int getCodigoEmpleado() {
        return CodigoEmpleado;
    }

    public void setCodigoEmpleado(int CodigoEmpleado) {
        this.CodigoEmpleado = CodigoEmpleado;
    }

    public String getNombreEmpleado() {
        return NombreEmpleado;
    }

    public void setNombreEmpleado(String NombreEmpleado) {
        this.NombreEmpleado = NombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return ApellidoEmpleado;
    }

    public void setApellidoEmpleado(String ApellidoEmpleado) {
        this.ApellidoEmpleado = ApellidoEmpleado;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

    public String getTelefonoEmpleado() {
        return TelefonoEmpleado;
    }

    public void setTelefonoEmpleado(String TelefonoEmpleado) {
        this.TelefonoEmpleado = TelefonoEmpleado;
    }

    public String getCorreoEmpleado() {
        return CorreoEmpleado;
    }

    public void setCorreoEmpleado(String CorreoEmpleado) {
        this.CorreoEmpleado = CorreoEmpleado;
    }
    
    
}
