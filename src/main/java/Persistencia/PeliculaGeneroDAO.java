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

    public boolean asignarGeneroAPelicula(int idPelicula, int idGenero) {
        String sql = "{CALL sp_asignar_genero_a_pelicula(?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexion (asignarGeneroAPelicula).");
                return false;
            }

            cstmt.setInt(1, idPelicula);
            cstmt.setInt(2, idGenero);
            cstmt.executeUpdate();
            System.out.println("Asignación genero ID " + idGenero + " a pelcula ID " + idPelicula + " intentada.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al asignar genero a película: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerGeneroDePelicula(int idPelicula, int idGenero) {
        String sql = "{CALL sp_remover_genero_de_pelicula(?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexion (removerGeneroDePelicula).");
                return false;
            }

            cstmt.setInt(1, idPelicula);
            cstmt.setInt(2, idGenero);
            int filasAfectadas = cstmt.executeUpdate();
            System.out.println("Remocion genero ID " + idGenero + " de pelicula ID " + idPelicula + " afecto " + filasAfectadas + " filas.");
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL al remover genero de pelicula: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerTodosGenerosDePelicula(int idPelicula) {
        String sql = "{CALL sp_remover_todos_generos_de_pelicula(?)}";
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexion (removerTodosGenerosDePelicula).");
                return false;
            }
            cstmt.setInt(1, idPelicula);
            cstmt.executeUpdate();
            System.out.println("Removidos todos los generos de la pelicula ID " + idPelicula);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al remover todos los generos de la pelicula: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> obtenerIdsGenerosDePelicula(int idPelicula) {
        List<Integer> idsGeneros = new ArrayList<>();
        String sql = "{CALL sp_obtener_ids_generos_de_pelicula(?)}";
        ResultSet rs = null;

        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexion (obtenerIdsGenerosDePelicula).");
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
            System.err.println("Error SQL al obtener IDs de generos de pelicula: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return idsGeneros;
    }

    public List<generoDTO> obtenerGenerosDePelicula(int idPelicula) {
        List<generoDTO> generos = new ArrayList<>();
        String sql = "{CALL sp_obtener_generos_completos_de_pelicula(?)}";
        ResultSet rs = null;

        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexion (obtenerGenerosDePelicula).");
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
            System.err.println("Error SQL al obtener generos completos de pelicula: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return generos;
    }
}
