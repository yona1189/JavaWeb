/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() {
        this.conexion = ConexionDB.obtenerConexion();
    }

  
    
public List<Usuario> obtenerUsuarios() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuarios";
    
    try (Connection conn = ConexionDB.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Usuario usuario = new Usuario(
                //Los nombres de los campos que estan entre "" deben ser igual a como estan en la base de datos
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getString("correo"),
                rs.getString("contraseña"),
                rs.getDate("fecha_registro")
                    
            );
            lista.add(usuario);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

public Usuario obtenerUsuarioPorId(int id) {
    Usuario usuario = null;
    String sql = "SELECT * FROM usuarios WHERE id = ?";
    
    try (Connection conn = ConexionDB.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setEmail(rs.getString("correo"));
            usuario.setContrasenia(rs.getString("contrasenia"));
            usuario.setFechaRegistro(rs.getDate("fecha_registro"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return usuario;
}

    // Método para insertar un usuario
public boolean insertarUsuario(Usuario usuario) {
    String sql = "INSERT INTO usuarios (nombre, correo, contraseña, fecha_registro) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getEmail());
        ps.setString(3, usuario.getContrasenia());
        ps.setDate(4, new java.sql.Date(usuario.getFechaRegistro().getTime())); 

        return ps.executeUpdate() > 0; // Devuelve true si se insertó con éxito
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean actualizarUsuario(Usuario usuario) {
    String sql = "UPDATE usuarios SET nombre = ?, correo = ?, contraseña = ? WHERE id = ?";
    
      try (Connection conn = ConexionDB.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, usuario.getNombre());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getContrasenia());
        stmt.setInt(4, usuario.getId());

        int filasActualizadas = stmt.executeUpdate();
        return filasActualizadas > 0; // Devuelve true si al menos una fila fue actualizada

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean eliminarUsuario(int id) {
    String sql = "DELETE FROM usuarios WHERE id = ?";
    try (Connection conn = ConexionDB.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0; // Devuelve true si se eliminó correctamente
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}