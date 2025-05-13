/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.generoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneroDAO {

    public boolean agregarGenero(generoDTO genero) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO genero (nombre) VALUES (?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, genero.getNombre());

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    resultado = true;
                    System.out.println("Género agregado correctamente.");
                } else {
                    System.out.println("No se pudo agregar el género.");
                }
            } else {
                System.out.println("Error: No se pudo obtener la conexión a la base de datos.");
            }

        } catch (SQLException e) {
            System.err.println("Error al agregar el género: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el PreparedStatement: " + e.getMessage());
            }
        }

        return resultado;
    }

    public boolean obtenerGeneroPorId(generoDTO genero) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean resultado = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT id_genero, nombre FROM genero WHERE id_genero = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, genero.getIdGenero());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                genero.setNombre(rs.getString("nombre"));
                resultado = true;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el género: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }

    public boolean actualizarGenero(generoDTO genero) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE genero SET nombre = ? WHERE id_genero = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, genero.getNombre());
            pstmt.setInt(2, genero.getIdGenero());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar el género: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }

    public boolean eliminarGenero(generoDTO genero) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM genero WHERE id_genero = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, genero.getIdGenero());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar el género: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }
}
