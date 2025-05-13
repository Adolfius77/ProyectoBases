/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class DatabaseConnection {
private static  Connection connection = null;
private static final String URL = "jdbc:mysql://localhost:3306/Streamingdb";
private static final String USER = "root";
private static final String PASSWORD = "adolfo";

private DatabaseConnection(){
    
}
public static Connection getConnection(){
    if(connection == null ){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("conexion ala base de datos establecida");
        } catch (ClassNotFoundException e) {
            System.err.println("error no se encontro el driver de jdbc");
            e.printStackTrace();
        }catch(SQLException e){
            System.err.println("error al connectar con la base de datos");
            e.printStackTrace();
        }
    }
    return  connection;
}
public static  void closeConnection(){
    if(connection != null){
        try {
            connection.close();
            connection = null;
            System.out.println("conexion ala base datos cerrada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
