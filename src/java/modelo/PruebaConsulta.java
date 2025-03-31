/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Yona20
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PruebaConsulta {
    public static void main(String[] args) {
        Connection conn = ConexionDB.obtenerConexion();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + " | Nombre: " + rs.getString("nombre") + "| Correo: " + rs.getString("correo")  + "| fechaRegistro: " + rs.getDate("fecha_registro"));
                }
                ConexionDB.cerrarConexion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se pudo establecer la conexión.");
            
        }
    }
}