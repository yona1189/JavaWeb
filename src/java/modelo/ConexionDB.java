/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    
    private static final String URL = "jdbc:mysql://localhost:3306/prueba?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "Marian2016";
    private static Connection conexion = null;

    public static Connection obtenerConexion() {
         System.out.println("Intentando conectar a la base de datos...");

        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
                System.out.println("¡Conexión exitosa a MySQL!");
            } else {
                System.out.println("La conexión ya estaba establecida.");
            }
        } catch (ClassNotFoundException | SQLException e) {
             System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }


    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}