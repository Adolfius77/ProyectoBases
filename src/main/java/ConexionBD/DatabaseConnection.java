package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/Streamingdb";
    private static final String USER = "root";
    private static final String PASSWORD = "adolfo"; // Considera no tener contraseñas en el código fuente

    private DatabaseConnection() {} // Constructor privado

    public static Connection getConnection() {
        try {
            // Si la conexión es nula O SI ESTÁ CERRADA, intentar (re)establecerla.
            if (connection == null || connection.isClosed()) {
                System.out.println("DatabaseConnection: Conexión es nula o está cerrada. Reintentando conectar...");
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("DatabaseConnection: Nueva conexión establecida.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("DatabaseConnection Error: Driver JDBC MySQL no encontrado. Revisa dependencias.");
            e.printStackTrace();
            connection = null; // Asegurar que sea null si falla
        } catch (SQLException e) {
            System.err.println("DatabaseConnection Error: No se pudo conectar a la base de datos - " + e.getMessage());
            e.printStackTrace();
            connection = null; // Asegurar que sea null si falla
        }
        return connection;
    }

    public static void closeConnection() {
        // Este método cierra la conexión global. Debe usarse con extremo cuidado,
        // usualmente solo cuando la aplicación completa se está cerrando.
        // LOS MÉTODOS DAO INDIVIDUALES NO DEBEN LLAMAR A ESTO.
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("DatabaseConnection: Conexión compartida cerrada.");
                }
            } catch (SQLException e) {
                System.err.println("DatabaseConnection Error: Al cerrar la conexión - " + e.getMessage());
                e.printStackTrace();
            } finally {
                connection = null; // MUY IMPORTANTE para que getConnection() pueda reabrirla
            }
        }
    }
}