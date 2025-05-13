/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import ConexionBD.DatabaseConnection;
import DTOS.peliculaDTO;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class añadirPeliculaDAO {

    //agregar pelicula
    public boolean agregarPelicula(peliculaDTO pelicula) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;

        try {

            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                String sqlPelicula = "INSERT INTO pelicula (titulo,anio_estreno,pais_origen,id_productora) VALUES (? , ? , ? ,? )";
                pstmt = conn.prepareStatement(sqlPelicula);
                pstmt.setString(1, pelicula.getTitulo());
                pstmt.setInt(2, pelicula.getAnioEstreno());
                pstmt.setString(3, pelicula.getPaisOrigen());
                pstmt.setInt(4, pelicula.getIdProductora());

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    resultado = true;
                    System.out.println("pelicula agregada correctamente.");
                } else {
                    System.out.println("no se pudo agregar la pelicula intentelo denuevo");
                }
            } else {
                System.out.println("error no se puede obtener la conexion la base de datos");
            }

        } catch (SQLException e) {
            System.err.println("error al cargar la pelicula: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar el PreparedStatement: " + e.getMessage());
            }
        }
        return resultado;
    }

    //eliminar pelicula
    public boolean eliminarPelicula(peliculaDTO pelicula) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM  pelicula WHERE  id_pelicula = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pelicula.getIdPelicula());
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

    public peliculaDTO obtenerPeliculaPorID(int idPelicula) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        peliculaDTO pelicula = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT id_pelicula, titulo, anio_estreno,pais_origen,id_productora from pelicula WHERE id_pelicula = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idPelicula);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pelicula = new peliculaDTO(rs.getInt("id_pelicula"),
                        rs.getString("titulo"),
                        rs.getInt("anio_estreno"),
                        rs.getString("pais_origen"),
                        rs.getInt("id_productora")
                );
            }

        } catch (SQLException e) {
            System.err.println("error al obtener la pelicula por id: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pelicula;
    }

    //actualizar pelicula
    public boolean actualizarPelicula(peliculaDTO pelicula) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("error no se puede obetener la conexion con la base de datos");
                return false;
            }
            String sql = "UPDATE pelicula SET titulo = ?, anio_estreno = ?, pais_origen = ?, id_productora = ? WHERE id_pelicula = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setInt(2, pelicula.getAnioEstreno());
            pstmt.setString(3, pelicula.getPaisOrigen());
            pstmt.setInt(4, pelicula.getIdProductora());
            pstmt.setInt(5, pelicula.getIdPelicula());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Película actualizada correctamente.");
            } else {
                System.out.println("No se actualizó ninguna película (puede que el ID no exista o los datos sean los mismos).");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar la película: " + e.getMessage());
            e.printStackTrace(); // Imprime el error SQL completo
        } catch (Exception e) {
            System.err.println("Error inesperado al actualizar la película: " + e.getMessage());
            e.printStackTrace(); // Otros posibles errores
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                // No cierres 'conn' aquí si DatabaseConnection la maneja como Singleton
                // o si se espera que siga abierta para otras operaciones.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }
}
