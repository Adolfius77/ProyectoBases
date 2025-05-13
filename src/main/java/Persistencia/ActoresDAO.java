/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.actorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author USER1
 */
public class ActoresDAO {

    public boolean obtenerActorPorId(actorDTO actor) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT id_actor, nombre, nacionalidad FROM actor WHERE id_actor = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actor.getIdActor());
            int filasAfectadas = pstmt.executeUpdate(); // Este debe ser executeQuery()
            if (filasAfectadas > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
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

    public boolean agregarActor(actorDTO actor) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO actor (nombre, nacionalidad) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, actor.getNombre());
            pstmt.setString(2, actor.getNacionalidad());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
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

    public boolean actualizarActor(actorDTO actor) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE actor SET nombre = ?, nacionalidad = ? WHERE id_actor = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, actor.getNombre());
            pstmt.setString(2, actor.getNacionalidad());
            pstmt.setInt(3, actor.getIdActor());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
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

    public boolean eliminarActor(actorDTO actor) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM actor WHERE id_actor = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actor.getIdActor());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
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
