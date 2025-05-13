/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;

/**
 *
 * @author USER
 */
public class peliculaDTO {

    private int idPelicula;
    private String titulo;
    private int anioEstreno; 
    private String paisOrigen;
    private int idProductora;  

    // Constructor
    public peliculaDTO(int idPelicula, String titulo, int anioEstreno, String paisOrigen, int idProductora) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.anioEstreno = anioEstreno;
        this.paisOrigen = paisOrigen;
        this.idProductora = idProductora;
    }


    // Getters y Setters
    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(int anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public int getIdProductora() {
        return idProductora;
    }

    public void setIdProductora(int idProductora) {
        this.idProductora = idProductora;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", anioEstreno=" + anioEstreno +
                ", paisOrigen='" + paisOrigen + '\'' +
                ", idProductora=" + idProductora +
                '}';
    }
}