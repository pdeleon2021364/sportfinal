
package org.diegocoyote.models;

import java.time.LocalDate;


/**
 *
 * @author juand
 */
public class Mascotas {
    private int CodigoMascota;
    private String NombreMascota;
    private String Especie;
    private String Raza;
    private String Sexo;
    private LocalDate FechaNacimiento;
    private String Color;
    private Double PesoActualKg;
    private int CodigoCliente;

    public Mascotas() {
    }

    public Mascotas(int CodigoMascota, String NombreMascota, String Especie, String Raza, String Sexo, LocalDate FechaNacimiento, String Color, Double PesoActualKg, int CodigoCliente) {
        this.CodigoMascota = CodigoMascota;
        this.NombreMascota = NombreMascota;
        this.Especie = Especie;
        this.Raza = Raza;
        this.Sexo = Sexo;
        this.FechaNacimiento = FechaNacimiento;
        this.Color = Color;
        this.PesoActualKg = PesoActualKg;
        this.CodigoCliente = CodigoCliente;
    }

    public int getCodigoMascota() {
        return CodigoMascota;
    }

    public void setCodigoMascota(int CodigoMascota) {
        this.CodigoMascota = CodigoMascota;
    }

    public String getNombreMascota() {
        return NombreMascota;
    }

    public void setNombreMascota(String NombreMascota) {
        this.NombreMascota = NombreMascota;
    }

    public String getEspecie() {
        return Especie;
    }

    public void setEspecie(String Especie) {
        this.Especie = Especie;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String Raza) {
        this.Raza = Raza;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public Double getPesoActualKg() {
        return PesoActualKg;
    }

    public void setPesoActualKg(Double PesoActualKg) {
        this.PesoActualKg = PesoActualKg;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    
}
