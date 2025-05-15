package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.actorDTO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ActoresDAO {

    public boolean agregarActor(actorDTO actor) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_agregar_actor(?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (agregarActor).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, actor.getNombre());
            cstmt.setString(2, actor.getNacionalidad());
            cstmt.registerOutParameter(3, Types.INTEGER);

            cstmt.execute();
            int nuevoId = cstmt.getInt(3);

            if (nuevoId > 0) {
                actor.setIdActor(nuevoId);
                resultado = true;
                System.out.println("Actor agregado con ID: " + nuevoId);
            } else {
                System.out.println("No se pudo agregar el actor.");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en agregarActor: " + e.getMessage());
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

    public actorDTO obtenerActorPorId(int idActor) {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        actorDTO actor = null;
        String sql = "{CALL sp_obtener_actor_por_id(?)}";

        try {
            conn = DatabaseConnection.getConnection();
             if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerActorPorId).");
                return null;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idActor);
            
            boolean hadResults = cstmt.execute();
            if (hadResults) {
                rs = cstmt.getResultSet();
                if (rs.next()) {
                    actor = new actorDTO(
                        rs.getInt("id_actor"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerActorPorId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return actor;
    }
    
    public List<actorDTO> obtenerTodosLosActores() {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<actorDTO> listaActores = new ArrayList<>();
        String sql = "{CALL sp_obtener_todos_los_actores()}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (obtenerTodosLosActores).");
                return listaActores; // Devuelve lista vacía
            }
            cstmt = conn.prepareCall(sql);
            boolean hadResults = cstmt.execute();

            if (hadResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    actorDTO actor = new actorDTO(
                        rs.getInt("id_actor"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad")
                    );
                    listaActores.add(actor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en obtenerTodosLosActores: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaActores;
    }

    public boolean actualizarActor(actorDTO actor) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_actualizar_actor(?, ?, ?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (actualizarActor).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, actor.getIdActor());
            cstmt.setString(2, actor.getNombre());
            cstmt.setString(3, actor.getNacionalidad());

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Actor actualizado con ID: " + actor.getIdActor());
            } else {
                 System.out.println("No se actualizó el actor (ID no encontrado o datos iguales).");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en actualizarActor: " + e.getMessage());
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

    public boolean eliminarActor(int idActor) {
        Connection conn = null;
        CallableStatement cstmt = null;
        boolean resultado = false;
        String sql = "{CALL sp_eliminar_actor(?)}";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Error: No se pudo obtener la conexión (eliminarActor).");
                return false;
            }
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, idActor);

            int filasAfectadas = cstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Actor eliminado con ID: " + idActor);
            } else {
                System.out.println("No se eliminó el actor (ID no encontrado).");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en eliminarActor: " + e.getMessage());
            
            e.getErrorCode();
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
    public List<actorDTO> buscarActoresPorNombre(String nombreActor) {
    Connection conn = null;
    CallableStatement cstmt = null;
    ResultSet rs = null;
    List<actorDTO> actoresEncontrados = new ArrayList<>();
    String sql = "{CALL sp_buscar_actores_por_nombre(?)}";

    try {
        conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.err.println("Error: No se pudo obtener la conexión (buscarActoresPorNombre).");
            return actoresEncontrados;
        }
        cstmt = conn.prepareCall(sql);
        cstmt.setString(1, nombreActor);

        boolean hadResults = cstmt.execute();
        if (hadResults) {
            rs = cstmt.getResultSet();
            while (rs.next()) {
                actorDTO actor = new actorDTO(
                    rs.getInt("id_actor"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad")
                );
                actoresEncontrados.add(actor);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error SQL en buscarActoresPorNombre: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (cstmt != null) cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return actoresEncontrados;
}
}