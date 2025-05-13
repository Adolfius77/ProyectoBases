/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;

/**
 *
 * @author USER
 */
public class productoraDTO {
    private int idProductora;
    private String nombre;
    private String pais;

    
    public productoraDTO(int idProductora, String nombre, String pais) {
        this.idProductora = idProductora;
        this.nombre = nombre;
        this.pais = pais;
    }

    public productoraDTO() {
    }

   
    public int getIdProductora() {
        return idProductora;
    }

    public void setIdProductora(int idProductora) {
        this.idProductora = idProductora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Productora{" +
                "idProductora=" + idProductora +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
