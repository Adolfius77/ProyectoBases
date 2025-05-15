package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.generoDTO; // Asegúrate que este DTO exista y tenga idGenero y nombre
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaGeneroDAO {

    /**
     * Asigna un género específico a una película específica.
     * No hace nada si la asignación ya existe.
     * @param idPelicula El ID de la película.
     * @param idGenero El ID del género.
     * @return true si la operación fue exitosa (o la asignación ya existía), false en caso de error.
     */
    public boolean asignarGeneroAPelicula(int idPelicula, int idGenero) {
        String sql = "{CALL sp_asignar_genero_a_pelicula(?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (asignarGeneroAPelicula).");
                return false;
            }

            cstmt.setInt(1, idPelicula);
            cstmt.setInt(2, idGenero);
            cstmt.executeUpdate(); // Se usa executeUpdate para INSERTs que no devuelven ResultSet complejo
            System.out.println("Asignación género ID " + idGenero + " a película ID " + idPelicula + " intentada.");
            return true; // Asumimos éxito si no hay excepción, ya que el SP maneja duplicados
        } catch (SQLException e) {
            System.err.println("Error SQL al asignar género a película: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remueve una asignación específica de género de una película.
     * @param idPelicula El ID de la película.
     * @param idGenero El ID del género.
     * @return true si se eliminó al menos una fila, false en caso contrario o si hubo error.
     */
    public boolean removerGeneroDePelicula(int idPelicula, int idGenero) {
        String sql = "{CALL sp_remover_genero_de_pelicula(?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (removerGeneroDePelicula).");
                return false;
            }

            cstmt.setInt(1, idPelicula);
            cstmt.setInt(2, idGenero);
            int filasAfectadas = cstmt.executeUpdate();
            System.out.println("Remoción género ID " + idGenero + " de película ID " + idPelicula + " afectó " + filasAfectadas + " filas.");
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL al remover género de película: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remueve todas las asignaciones de géneros para una película específica.
     * Útil antes de reasignar un nuevo conjunto de géneros.
     * @param idPelicula El ID de la película.
     * @return true si la operación fue exitosa (incluso si no había géneros que remover), false en caso de error.
     */
    public boolean removerTodosGenerosDePelicula(int idPelicula) {
        String sql = "{CALL sp_remover_todos_generos_de_pelicula(?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (removerTodosGenerosDePelicula).");
                return false;
            }
            cstmt.setInt(1, idPelicula);
            cstmt.executeUpdate(); // executeUpdate es adecuado, las filas afectadas pueden ser 0 o más
            System.out.println("Removidos todos los géneros de la película ID " + idPelicula);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al remover todos los géneros de la película: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista de los IDs de todos los géneros asignados a una película específica.
     * @param idPelicula El ID de la película.
     * @return Una lista de Integers representando los IDs de los géneros. Lista vacía si no hay o en caso de error.
     */
    public List<Integer> obtenerIdsGenerosDePelicula(int idPelicula) {
        List<Integer> idsGeneros = new ArrayList<>();
        String sql = "{CALL sp_obtener_ids_generos_de_pelicula(?)}";
        ResultSet rs = null;

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerIdsGenerosDePelicula).");
                return idsGeneros;
            }
            cstmt.setInt(1, idPelicula);
            
            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    idsGeneros.add(rs.getInt("id_genero"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener IDs de géneros de película: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            // El CallableStatement se cierra por el try-with-resources
        }
        return idsGeneros;
    }

    /**
     * Obtiene una lista de objetos generoDTO completos asignados a una película específica.
     * @param idPelicula El ID de la película.
     * @return Una lista de objetos generoDTO. Lista vacía si no hay géneros asignados o en caso de error.
     */
    public List<generoDTO> obtenerGenerosDePelicula(int idPelicula) {
        List<generoDTO> generos = new ArrayList<>();
        String sql = "{CALL sp_obtener_generos_completos_de_pelicula(?)}";
        ResultSet rs = null;

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerGenerosDePelicula).");
                return generos;
            }
            cstmt.setInt(1, idPelicula);

            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    generoDTO genero = new generoDTO(
                        rs.getInt("id_genero"),
                        rs.getString("nombre")
                    );
                    generos.add(genero);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener géneros completos de película: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            // El CallableStatement se cierra por el try-with-resources
        }
        return generos;
    }
}