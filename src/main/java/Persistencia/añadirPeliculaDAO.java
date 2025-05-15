package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.peliculaDTO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class añadirPeliculaDAO {

    public boolean agregarPelicula(peliculaDTO pelicula) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_agregar_pelicula(?, ?, ?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }

            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, pelicula.getTitulo());
            cstmt.setInt(2, pelicula.getAnioEstreno());
            cstmt.setString(3, pelicula.getPaisOrigen());
            cstmt.setInt(4, pelicula.getIdProductora());
            cstmt.registerOutParameter(5, Types.INTEGER); // p_nueva_id_pelicula

            cstmt.execute();
            int nuevoId = cstmt.getInt(5);

            if (nuevoId > 0) {
                pelicula.setIdPelicula(nuevoId);
                resultado = true;
                System.out.println("Película agregada con ID: " + nuevoId);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en agregarPelicula: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar cstmt
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public peliculaDTO obtenerPeliculaPorID(int idPelicula) { // Coincide con el llamado del panel
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        peliculaDTO pelicula = null;
        String sql = "{CALL sp_obtener_pelicula_por_id(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return null;
            }

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idPelicula);

            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                if (rs.next()) {
                    pelicula = new peliculaDTO(
                            rs.getInt("id_pelicula"),
                            rs.getString("titulo"),
                            rs.getInt("anio_estreno"),
                            rs.getString("pais_origen"),
                            rs.getInt("id_productora")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerPeliculaPorID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar rs, cstmt
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pelicula;
    }

    public List<peliculaDTO> obtenerTodasLasPeliculas() {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<peliculaDTO> listaPeliculas = new ArrayList<>();
        String sql = "{CALL sp_obtener_todas_las_peliculas()}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return listaPeliculas;
            }

            cstmt = conn.prepareCall(sql);
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
                    listaPeliculas.add(pelicula);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerTodasLasPeliculas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar rs, cstmt
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaPeliculas;
    }

    public boolean actualizarPelicula(peliculaDTO pelicula) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_actualizar_pelicula(?, ?, ?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, pelicula.getIdPelicula());
            cstmt.setString(2, pelicula.getTitulo());
            cstmt.setInt(3, pelicula.getAnioEstreno());
            cstmt.setString(4, pelicula.getPaisOrigen());
            cstmt.setInt(5, pelicula.getIdProductora());

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Película actualizada ID: " + pelicula.getIdPelicula());
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en actualizarPelicula: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar cstmt
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public boolean eliminarPelicula(int idPelicula) { // Cambiado para aceptar solo ID
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_eliminar_pelicula(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idPelicula);

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Película eliminada ID: " + idPelicula);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en eliminarPelicula: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar cstmt
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }
    public List<peliculaDTO> buscarPeliculasPorTitulo(String terminoBusqueda) {
    Connection conn = null;
    CallableStatement cstmt = null;
    ResultSet rs = null;
    List<peliculaDTO> peliculasEncontradas = new ArrayList<>();
    String sql = "{CALL sp_buscar_peliculas_por_titulo(?)}";

    try {
        conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.err.println("Error: No se pudo obtener la conexión (buscarPeliculasPorTitulo).");
            return peliculasEncontradas;
        }

        cstmt = conn.prepareCall(sql);
        cstmt.setString(1, terminoBusqueda);

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
                peliculasEncontradas.add(pelicula);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error SQL en buscarPeliculasPorTitulo: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (cstmt != null) cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return peliculasEncontradas;
}
    public List<peliculaDTO> obtenerPeliculasPorGenero(int idGenero) {
    List<peliculaDTO> peliculas = new ArrayList<>();
    String sql = "{CALL sp_obtener_peliculas_por_genero(?)}";
    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {
        cstmt.setInt(1, idGenero);
        boolean hadResults = cstmt.execute();
        if (hadResults) {
            try (ResultSet rs = cstmt.getResultSet()) {
                while (rs.next()) {
                    peliculas.add(new peliculaDTO(
                        rs.getInt("id_pelicula"),
                        rs.getString("titulo"),
                        rs.getInt("anio_estreno"),
                        rs.getString("pais_origen"),
                        rs.getInt("id_productora")
                    ));
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener películas por género: " + e.getMessage());
        e.printStackTrace();
    }
    return peliculas;
}
 public List<peliculaDTO> obtenerPeliculasPorActor(int idActor) {
        List<peliculaDTO> peliculas = new ArrayList<>();
        String sql = "{CALL sp_obtener_peliculas_por_actor(?)}";
        ResultSet rs = null;

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerPeliculasPorActor).");
                return peliculas; // Devuelve lista vacía
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
            System.err.println("Error SQL al obtener películas por actor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return peliculas;
    }
 public List<peliculaDTO> obtenerPeliculasPorProductora(int idProductora) {
        List<peliculaDTO> peliculas = new ArrayList<>();
        String sql = "{CALL sp_obtener_peliculas_por_productora(?)}";
        ResultSet rs = null;

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerPeliculasPorProductora).");
                return peliculas; // Devuelve lista vacía
            }
            
            cstmt.setInt(1, idProductora);

            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    peliculaDTO pelicula = new peliculaDTO(
                        rs.getInt("id_pelicula"),
                        rs.getString("titulo"),
                        rs.getInt("anio_estreno"),
                        rs.getString("pais_origen"),
                        rs.getInt("id_productora") // Esta ya es parte de la tabla pelicula
                    );
                    peliculas.add(pelicula);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener películas por productora: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                 // CallableStatement se cierra automáticamente por el try-with-resources
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return peliculas;
    }
 public List<peliculaDTO> obtenerPeliculasPorRangoDeAnios(int anioInicio, int anioFin) {
    List<peliculaDTO> peliculas = new ArrayList<>();
    String sql = "{CALL sp_obtener_peliculas_por_rango_anios(?, ?)}";
     try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {
        cstmt.setInt(1, anioInicio);
        cstmt.setInt(2, anioFin);
        boolean hadResults = cstmt.execute();
        if (hadResults) {
            try (ResultSet rs = cstmt.getResultSet()) {
                while (rs.next()) {
                    peliculas.add(new peliculaDTO(
                        rs.getInt("id_pelicula"),
                        rs.getString("titulo"),
                        rs.getInt("anio_estreno"),
                        rs.getString("pais_origen"),
                        rs.getInt("id_productora")
                    ));
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener películas por rango de años: " + e.getMessage());
        e.printStackTrace();
    }
    return peliculas;
}
}
