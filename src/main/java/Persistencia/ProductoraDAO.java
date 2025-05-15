package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.productoraDTO; 
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProductoraDAO {

    public boolean agregarProductora(productoraDTO productora) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_agregar_productora(?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return false;

            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, productora.getNombre());
            cstmt.setString(2, productora.getPais());
            cstmt.registerOutParameter(3, Types.INTEGER);

            cstmt.execute();
            int nuevoId = cstmt.getInt(3);

            if (nuevoId > 0) {
                productora.setIdProductora(nuevoId); 
                resultado = true;
                System.out.println("Productora agregada con ID: " + nuevoId);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en agregarProductora: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public productoraDTO obtenerProductoraPorId(int idProductora) {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        productoraDTO productora = null;
        String sql = "{CALL sp_obtener_productora_por_id(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return null;

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idProductora);
            
            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                if (rs.next()) {
                    productora = new productoraDTO();
                    productora.setIdProductora(rs.getInt("id_productora"));
                    productora.setNombre(rs.getString("nombre"));
                    productora.setPais(rs.getString("pais"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerProductoraPorId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productora;
    }
    
    public List<productoraDTO> obtenerTodasLasProductoras() {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<productoraDTO> listaProductoras = new ArrayList<>();
        String sql = "{CALL sp_obtener_todas_las_productoras()}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return listaProductoras; 

            cstmt = conn.prepareCall(sql);
            boolean hadResults = cstmt.execute();

            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    productoraDTO productora = new productoraDTO();
                    productora.setIdProductora(rs.getInt("id_productora"));
                    productora.setNombre(rs.getString("nombre"));
                    productora.setPais(rs.getString("pais"));
                    listaProductoras.add(productora);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerTodasLasProductoras: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaProductoras;
    }

    public boolean actualizarProductora(productoraDTO productora) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_actualizar_productora(?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return false;

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, productora.getIdProductora());
            cstmt.setString(2, productora.getNombre());
            cstmt.setString(3, productora.getPais());

            int filasAfectadas = cstmt.executeUpdate(); 
            if (filasAfectadas > 0) {
                resultado = true;
                 System.out.println("Productora actualizada con ID: " + productora.getIdProductora());
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en actualizarProductora: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public boolean eliminarProductora(int idProductora) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_eliminar_productora(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return false;

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idProductora);

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Productora eliminada con ID: " + idProductora);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en eliminarProductora: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }
    public List<productoraDTO> buscarProductorasPorNombre(String nombreProductora) {
    Connection conn = null;
    CallableStatement cstmt = null;
    ResultSet rs = null;
    List<productoraDTO> productorasEncontradas = new ArrayList<>();
    String sql = "{CALL sp_buscar_productoras_por_nombre(?)}";

    try {
        conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.err.println("Error: No se pudo obtener la conexión (buscarProductorasPorNombre).");
            return productorasEncontradas;
        }
        cstmt = conn.prepareCall(sql);
        cstmt.setString(1, nombreProductora);

        boolean hadResults = cstmt.execute();
        if (hadResults) {
            rs = cstmt.getResultSet();
            while (rs.next()) {
                productoraDTO productora = new productoraDTO(); 
                productora.setIdProductora(rs.getInt("id_productora"));
                productora.setNombre(rs.getString("nombre"));
                productora.setPais(rs.getString("pais"));
                productorasEncontradas.add(productora);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error SQL en buscarProductorasPorNombre: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (cstmt != null) cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return productorasEncontradas;
}
}