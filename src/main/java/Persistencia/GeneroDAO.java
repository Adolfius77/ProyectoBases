package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.generoDTO; 
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO {

    public boolean agregarGenero(generoDTO genero) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_agregar_genero(?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (agregarGenero).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, genero.getNombre());
            cstmt.registerOutParameter(2, Types.INTEGER); 
            cstmt.execute();
            int nuevoId = cstmt.getInt(2);

            if (nuevoId > 0) {
                genero.setIdGenero(nuevoId);
                resultado = true;
                System.out.println("Género agregado con ID: " + nuevoId);
            } else {
                 System.out.println("No se pudo agregar el género.");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en agregarGenero: " + e.getMessage());
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

    public generoDTO obtenerGeneroPorId(int idGenero) {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        generoDTO genero = null;
        String sql = "{CALL sp_obtener_genero_por_id(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerGeneroPorId).");
                return null;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idGenero);
            
            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                if (rs.next()) {
                    genero = new generoDTO(
                        rs.getInt("id_genero"),
                        rs.getString("nombre")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerGeneroPorId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return genero;
    }
    
    public List<generoDTO> obtenerTodosLosGeneros() {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<generoDTO> listaGeneros = new ArrayList<>();
        String sql = "{CALL sp_obtener_todos_los_generos()}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerTodosLosGeneros).");
                return listaGeneros; // Devuelve lista vacía
            }
            cstmt = conn.prepareCall(sql);
            boolean hadResults = cstmt.execute();

            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    generoDTO genero = new generoDTO(
                        rs.getInt("id_genero"),
                        rs.getString("nombre")
                    );
                    listaGeneros.add(genero);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerTodosLosGeneros: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaGeneros;
    }

    public boolean actualizarGenero(generoDTO genero) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_actualizar_genero(?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (actualizarGenero).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, genero.getIdGenero());
            cstmt.setString(2, genero.getNombre());

            int filasAfectadas = cstmt.executeUpdate(); 
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Género actualizado con ID: " + genero.getIdGenero());
            } else {
                System.out.println("No se actualizó el género (ID no encontrado o nombre igual).");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en actualizarGenero: " + e.getMessage());
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

    public boolean eliminarGenero(int idGenero) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_eliminar_genero(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (eliminarGenero).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idGenero);

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Género eliminado con ID: " + idGenero);
            } else {
                System.out.println("No se eliminó el género (ID no encontrado).");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en eliminarGenero: " + e.getMessage());
     
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
    public List<generoDTO> buscarGenerosPorNombre(String nombreGenero) {
    Connection conn = null;
    CallableStatement cstmt = null;
    ResultSet rs = null;
    List<generoDTO> generosEncontrados = new ArrayList<>();
    String sql = "{CALL sp_buscar_generos_por_nombre(?)}";

    try {
        conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.err.println("Error: No se pudo obtener la conexión (buscarGenerosPorNombre).");
            return generosEncontrados;
        }
        cstmt = conn.prepareCall(sql);
        cstmt.setString(1, nombreGenero);

        boolean hadResults = cstmt.execute();
        if (hadResults) {
            rs = cstmt.getResultSet();
            while (rs.next()) {
                generoDTO genero = new generoDTO(
                    rs.getInt("id_genero"),
                    rs.getString("nombre")
                );
                generosEncontrados.add(genero);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error SQL en buscarGenerosPorNombre: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (cstmt != null) cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return generosEncontrados;
}
}