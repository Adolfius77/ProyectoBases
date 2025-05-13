/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.dirrectorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class directoresDAO {

    //agregar director
    public boolean agregarDirector(dirrectorDTO dirrector) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO director (nombre, nacionalidad) VALUES (?, ?)"; // CORREGIDO a "director"
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dirrector.getNombre());
            pstmt.setString(2, dirrector.getNacionalidad());
            int filasAfectadas = pstmt.executeUpdate(); // Esta es la línea 30 según tu traza
            if (filasAfectadas > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    //actualizar directors
    public boolean actualizarDirrector(dirrectorDTO dirrector) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();

            String sql = "UPDATE director SET nombre = ?, nacionalidad = ? WHERE id_director = ?"; // Nombre de tabla "director" es correcto
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dirrector.getNombre());
            pstmt.setString(2, dirrector.getNacionalidad());
            pstmt.setInt(3, dirrector.getIdDirector());
            int filaAfectadas = pstmt.executeUpdate();
            if (filaAfectadas > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    // eliminar director
   public boolean eliminar(dirrectorDTO directorDTOobj){ 
    Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
           
            String sql = "DELETE FROM director WHERE id_director = ?"; 
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, directorDTOobj.getIdDirector()); 
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }
}