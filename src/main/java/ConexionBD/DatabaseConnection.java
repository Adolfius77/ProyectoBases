package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    private static final String URL = "jdbc:mysql://localhost:3306/Streamingdb";
    private static final String USER = "root";
    private static final String PASSWORD = "adolfo"; //cambiar a vacio si no tiene contra o cambiar si tienes una diferente no se les olvide

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {

            if (connection == null || connection.isClosed()) {
                System.out.println("DatabaseConnection: Conexion es nula o esta cerrada. Abriendo nueva conexion...");
                // Cargar el driver JDBC de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("DatabaseConnection: Nueva conexión a la base de datos establecida.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("DatabaseConnection ERROR CRITICO: Driver JDBC MySQL no encontrado. Asegurate de que la dependencia de MySQL Connector/J esté en tu pom.xml.");
            e.printStackTrace();
            connection = null;
        } catch (SQLException e) {
            System.err.println("DatabaseConnection ERROR CRÍTICO: No se pudo conectar a la base de datos - " + e.getMessage());
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }

    public static void closeConnection() {

        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("DatabaseConnection: Conexion compartida CERRADA explicitamente.");
                }
            } catch (SQLException e) {
                System.err.println("DatabaseConnection ERROR al cerrar la conexion compartida: " + e.getMessage());
                e.printStackTrace();
            } finally {

                connection = null;
            }
        }
    }
}
