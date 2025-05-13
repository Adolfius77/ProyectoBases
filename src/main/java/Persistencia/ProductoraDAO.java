/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.productoraDTO;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author USER1
 */
public class ProductoraDAO {

    public boolean obtenerProductoraPorId(productoraDTO productora) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT  id_productora, nombre FROM productora WHERE id_productora = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productora.getIdProductora());
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

    public boolean agregarProductora(productoraDTO productora) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO productora (nombre, pais) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productora.getNombre());
            pstmt.setString(2, productora.getPais());

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

    public boolean actualizarProductora(productoraDTO productora) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE  productora SET nombre = ?, pais = ? WHERE id_productora= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productora.getNombre());
            pstmt.setString(2, productora.getPais());
            pstmt.setInt(3, productora.getIdProductora());
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

    public boolean eliminarProductora(productoraDTO productora) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM productora WHERE id_productora = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productora.getIdProductora());
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

//    public List<productoraDTO> obtenerTodasProductoras() {
//        List<productoraDTO> productoras = new ArrayList<>();
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            conn = DatabaseConnection.getConnection();
//            String sql = "SELECT id_productora, nombre FROM productora";
//            pstmt = conn.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                productoraDTO productora = new productoraDTO();
//                productora.setIdProductora(rs.getInt("id_productora"));
//                productora.setNombre(rs.getString("nombre"));
//                productoras.add(productora);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                // Do NOT close the connection here if using Singleton
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return productoras;
//    }
}
