/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import modelo.Usuario;
import modelo.UsuarioDAO;


/**
 *
 * @author Yona20
 */

@WebServlet("/index")
public class UsuarioServlet extends HttpServlet {
    
   /*
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    
        // Obtener lista de usuarios para mostrar en index.jsp  
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> listaUsuarios = usuarioDAO.obtenerUsuarios(); // Método que obtiene los usuarios de la BD

  System.out.println("Usuarios obtenidos en Servlet: " + listaUsuarios.size());
        request.setAttribute("usuarios", listaUsuarios); // Enviamos la lista al JSP
       request.getRequestDispatcher("index.jsp").forward(request, response); // Redirigir a index.jsp
    }
*/
    
  @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    String idUsuario = request.getParameter("idUsuario");

    if (idUsuario != null) {
        // Buscar el usuario seleccionado y enviarlo al JSP
        Usuario usuarioSeleccionado = usuarioDAO.obtenerUsuarioPorId(Integer.parseInt(idUsuario));
        request.setAttribute("usuarioSeleccionado", usuarioSeleccionado);
    }

    // Obtener lista de usuarios y enviarlos al JSP
    List<Usuario> listaUsuarios = usuarioDAO.obtenerUsuarios();
    request.setAttribute("usuarios", listaUsuarios);

    request.getRequestDispatcher("index.jsp").forward(request, response);
}
        
        
  @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    
    String accion = request.getParameter("accion");
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    if ("eliminar".equals(accion)) {
        String idUsuario = request.getParameter("idUsuario");
        if (idUsuario != null && !idUsuario.isEmpty()) {
            boolean eliminado = usuarioDAO.eliminarUsuario(Integer.parseInt(idUsuario));
            if (eliminado) {
                response.sendRedirect("index"); // Recargar la página
                return;
            } else {
                response.getWriter().println("Error al eliminar usuario.");
                return;
            }
        }
    }

    // Resto de la lógica de inserción/actualización
    String idUsuario = request.getParameter("idUsuario");
    String nombre = request.getParameter("nombre");
    String correo = request.getParameter("correo");
    String contrasenia = request.getParameter("contrasenia");

    Usuario usuario = new Usuario();
    usuario.setNombre(nombre);
    usuario.setEmail(correo);
    usuario.setContrasenia(contrasenia);

    boolean resultado;
    if (idUsuario != null && !idUsuario.isEmpty()) {
        usuario.setId(Integer.parseInt(idUsuario));
        resultado = usuarioDAO.actualizarUsuario(usuario);
    } else {
        usuario.setFechaRegistro(new Date());
        resultado = usuarioDAO.insertarUsuario(usuario);
    }

    if (resultado) {
        response.sendRedirect("index");
    } else {
        response.getWriter().println("Error al procesar la operación.");
    }
}
     
     
}



