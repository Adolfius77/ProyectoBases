package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.actorDTO;
import DTOS.peliculaDTO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaActorDAO {

    public boolean asignarActorAPelicula(int idPelicula, int idActor) {
        String sql = "{CALL sp_asignar_actor_a_pelicula(?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {
            if (conn == null) {
                System.err.println("PeliculaActorDAO: Error de conexióo al asignar actor a pelicula.");
                return false;
            }
            cstmt.setInt(1, idPelicula);
            cstmt.setInt(2, idActor);
            cstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL en asignarActorAPelicula: " + e.getMessage() + " (Codigo: " + e.getErrorCode() + ")");

            e.printStackTrace();
            return false;
        }
    }

    public boolean removerTodasPeliculasDeActor(int idActor) {
        String sql = "{CALL sp_remover_todas_peliculas_de_actor(?)}";
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {
            if (conn == null) {
                System.err.println("PeliculaActorDAO: Error de conexion al remover peliculas de actor.");
                return false;
            }
            cstmt.setInt(1, idActor);
            cstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL en removerTodasPeliculasDeActor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<peliculaDTO> obtenerPeliculasDeActor(int idActor) {
        List<peliculaDTO> peliculas = new ArrayList<>();
        String sql = "{CALL sp_obtener_peliculas_de_actor(?)}";
        ResultSet rs = null;
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {
            if (conn == null) {
                System.err.println("PeliculaActorDAO: Error de conexion al obtener peliculas de actor.");
                return peliculas;
            }
            cstmt.setInt(1, idActor);
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
            System.err.println("Error SQL en obtenerPeliculasDeActor: " + e.getMessage());
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

    public List<Integer> obtenerIdsPeliculasDeActor(int idActor) {
        List<Integer> idsPeliculas = new ArrayList<>();
        String sql = "{CALL sp_obtener_ids_peliculas_de_actor(?)}";
        ResultSet rs = null;
        try (Connection conn = DatabaseConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(sql)) {
            if (conn == null) {
                System.err.println("PeliculaActorDAO: Error de conexion al obtener IDs de peliculas de actor.");
                return idsPeliculas;
            }
            cstmt.setInt(1, idActor);
            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    idsPeliculas.add(rs.getInt("id_pelicula"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerIdsPeliculasDeActor: " + e.getMessage());
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
        return idsPeliculas;
    }
}
