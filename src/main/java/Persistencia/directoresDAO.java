package Persistencia;

import ConexionBD.DatabaseConnection;

import DTOS.dirrectorDTO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class directoresDAO {

    public boolean agregarDirector(dirrectorDTO director) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_agregar_director(?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (agregarDirector).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, director.getNombre());
            cstmt.setString(2, director.getNacionalidad());
            cstmt.registerOutParameter(3, Types.INTEGER); // p_nuevo_id_director

            cstmt.execute();
            int nuevoId = cstmt.getInt(3);

            if (nuevoId > 0) {
                director.setIdDirector(nuevoId); // Actualizar DTO
                resultado = true;
                System.out.println("Director agregado con ID: " + nuevoId);
            } else {
                System.out.println("No se pudo agregar el director.");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en agregarDirector: " + e.getMessage());
            e.printStackTrace();
        } finally {
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

    public dirrectorDTO obtenerDirectorPorId(int idDirector) {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        dirrectorDTO director = null;
        String sql = "{CALL sp_obtener_director_por_id(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerDirectorPorId).");
                return null;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idDirector);

            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                if (rs.next()) {
                    director = new dirrectorDTO(
                            rs.getInt("id_director"),
                            rs.getString("nombre"),
                            rs.getString("nacionalidad")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerDirectorPorId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return director;
    }

    public List<dirrectorDTO> obtenerTodosLosDirectores() {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<dirrectorDTO> listaDirectores = new ArrayList<>();
        String sql = "{CALL sp_obtener_todos_los_directores()}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerTodosLosDirectores).");
                return listaDirectores;
            }
            cstmt = conn.prepareCall(sql);
            boolean hadResults = cstmt.execute();

            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    dirrectorDTO director = new dirrectorDTO(
                            rs.getInt("id_director"),
                            rs.getString("nombre"),
                            rs.getString("nacionalidad")
                    );
                    listaDirectores.add(director);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerTodosLosDirectores: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaDirectores;
    }

    public boolean actualizarDirector(dirrectorDTO director) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_actualizar_director(?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (actualizarDirector).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, director.getIdDirector());
            cstmt.setString(2, director.getNombre());
            cstmt.setString(3, director.getNacionalidad());

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Director actualizado con ID: " + director.getIdDirector());
            } else {
                System.out.println("No se actualizó el director (ID no encontrado o datos iguales).");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en actualizarDirector: " + e.getMessage());
            e.printStackTrace();
        } finally {
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

    public boolean eliminarDirector(int idDirector) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_eliminar_director(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (eliminarDirector).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idDirector);

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Director eliminado con ID: " + idDirector);
            } else {
                System.out.println("No se eliminó el director (ID no encontrado).");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en eliminarDirector: " + e.getMessage());
            e.printStackTrace();
        } finally {
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

    public List<dirrectorDTO> buscarDirectoresPorNombre(String nombreDirector) {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<dirrectorDTO> directoresEncontrados = new ArrayList<>();
        String sql = "{CALL sp_buscar_directores_por_nombre(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (buscarDirectoresPorNombre).");
                return directoresEncontrados;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, nombreDirector);

            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    dirrectorDTO director = new dirrectorDTO(
                            rs.getInt("id_director"),
                            rs.getString("nombre"),
                            rs.getString("nacionalidad")
                    );
                    directoresEncontrados.add(director);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en buscarDirectoresPorNombre: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return directoresEncontrados;
    }
}
