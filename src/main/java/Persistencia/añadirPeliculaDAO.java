/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.peliculaDTO;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class añadirPeliculaDAO {
    //agregar pelicula
    public boolean agregarPelicula(peliculaDTO pelicula) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;

        try {

            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                String sqlPelicula = "INSERT INTO pelicula (titulo,anio_estreno,pais_origen,id_productora) VALUES (? , ? , ? ,? )";
                pstmt = conn.prepareStatement(sqlPelicula);
                pstmt.setString(1, pelicula.getTitulo());
                pstmt.setInt(2,pelicula.getAnioEstreno());
                pstmt.setString(3, pelicula.getPaisOrigen());
                pstmt.setInt(4, pelicula.getIdProductora());
                
                int filasAfectadas = pstmt.executeUpdate();
                if(filasAfectadas > 0){
                    resultado = true;
                    System.out.println("pelicula agregada correctamente.");
                }else{
                    System.out.println("no se pudo agregar la pelicula intentelo denuevo");
                }
            }else{
                System.out.println("error no se puede obtener la conexion la base de datos");
            }

        } catch (SQLException e) {
            System.err.println("error al cargar la pelicula: " + e.getMessage());
            e.printStackTrace();
        }finally{
            try {
                if(pstmt != null )pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el PreparedStatement: " + e.getMessage());
            }
        }
        return resultado;
    }
    //eliminar pelicula
    public boolean eliminarPelicula(peliculaDTO pelicula ){
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
           conn = DatabaseConnection.getConnection();
           String sql = "DELETE FROM  pelicula WHERE  id_pelicula = ?";
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, pelicula.getIdPelicula());
           int filasAfectadas = pstmt.executeUpdate();
           if(filasAfectadas > 0){
               resultado = true;
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }
    //actualizar pelicula
    public boolean actualizarPelicula (peliculaDTO pelicula ){
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE pelicula SET titulo = ?, anio_estreno = ?, pais_origen= ?,  where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setInt(2, pelicula.getAnioEstreno());
            pstmt.setString(3, pelicula.getPaisOrigen());
            pstmt.setInt(4, pelicula.getIdPelicula());
            int filasAfectadas = pstmt.executeUpdate();
            if(filasAfectadas > 0 ){
                resultado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
}
}