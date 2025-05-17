package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.dirrectorDTO; // Necesitarás este DTO
import DTOS.peliculaDTO;  // Necesitarás este DTO
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDirectorDAO {

    public boolean asignarDirectorAPelicula(int idPelicula, int idDirector) {
        String sql = "{CALL sp_asignar_director_a_pelicula(?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {
            if (conn == null) {
                System.err.println("PeliculaDirectorDAO: Error de conexion al asignar director.");
                return false;
            }
            cstmt.setInt(1, idPelicula);
            cstmt.setInt(2, idDirector);
            cstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL en asignarDirectorAPelicula: " + e.getMessage() + " (Codigo: " + e.getErrorCode() + ")");
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerTodasPeliculasDeDirector(int idDirector) {
        String sql = "{CALL sp_remover_todas_peliculas_de_director(?)}";
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {
            if (conn == null) {
                System.err.println("PeliculaDirectorDAO: Error de conexion al remover peliculas de director.");
                return false;
            }
            cstmt.setInt(1, idDirector);
            cstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL en removerTodasPeliculasDeDirector: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<peliculaDTO> obtenerPeliculasDeDirector(int idDirector) {
        List<peliculaDTO> peliculas = new ArrayList<>();
        String sql = "{CALL sp_obtener_peliculas_de_director(?)}";
        ResultSet rs = null;
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {
            if (conn == null) {
                System.err.println("PeliculaDirectorDAO: Error de conexion al obtener peliculas de director.");
                return peliculas;
            }
            cstmt.setInt(1, idDirector);
            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    peliculaDTO pelicula = new peliculaDTO(
                            rs.getInt("id_pelicula"),
                            rs.getString("titulo"),
                            rs.getInt("anio_estreno"),
                            rs.getString("pais_origen"),
                            rs.getInt("id_productora")
                    );
                    peliculas.add(pelicula);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerPeliculasDeDirector: " + e.getMessage());
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
        return peliculas;
    }
}
