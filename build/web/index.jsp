<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, modelo.Usuario" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Gestión de Usuarios</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


        <!-- Enlace al archivo CSS personalizado -->
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>

        <div class="container">
            <h1 class="titulo1">Gestión de Usuarios</h1>

            <div class="row">
                <!-- Formulario de Registro -->
                <div class="col-md-4">
                    <div class="card p-3">
                        <h4 class="text-center text-secondary">
                            <%= request.getAttribute("usuarioSeleccionado") != null ? "Editar Usuario" : "Registrar Usuario"%>
                        </h4>
                        <form action="UsuarioServlet" method="post">
                            <!-- Campo oculto para almacenar el ID del usuario en caso de edición -->
                            <input type="hidden" name="idUsuario" value="<%= request.getAttribute("usuarioSeleccionado") != null ? ((Usuario) request.getAttribute("usuarioSeleccionado")).getId() : ""%>">

                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text" name="nombre" class="form-control" required 
                                       value="<%= request.getAttribute("usuarioSeleccionado") != null ? ((Usuario) request.getAttribute("usuarioSeleccionado")).getNombre() : ""%>">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Correo</label>
                                <input type="email" name="correo" class="form-control" required 
                                       value="<%= request.getAttribute("usuarioSeleccionado") != null ? ((Usuario) request.getAttribute("usuarioSeleccionado")).getEmail() : ""%>">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Contraseña</label>
                                <input type="password" name="contrasenia" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">
                                <%= request.getAttribute("usuarioSeleccionado") != null ? "Actualizar" : "Registrar"%>
                            </button>
                        </form>
                    </div>
                </div>

                <!-- Tabla de Usuarios -->
                <div class="col-md-8">
                    <div class="card p-3">
                        <h4 class="text-center text-secondary">Lista de Usuarios</h4>
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Correo</th>
                                    <th>Fecha de Registro</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<modelo.Usuario> usuarios = (List<modelo.Usuario>) request.getAttribute("usuarios");
                                    if (usuarios != null) {
                                        for (modelo.Usuario usuario : usuarios) {
                                %>
                                <tr>
                                    <td><%= usuario.getId()%></td>
                                    <td><%= usuario.getNombre()%></td>
                                    <td><%= usuario.getEmail()%></td>
                                    <td><%= usuario.getFechaRegistro()%></td>
                                    <td>
                                        <!-- Botón Editar -->
                                        <button class="btn btn-warning btn-sm" onclick="editarUsuario(<%= usuario.getId()%>)">Editar</button>
                                    </td>
                                    <td>
                                        <!-- Botón de eliminar con JavaScript -->
                                        <form action="UsuarioServlet" method="post" onsubmit="return confirm('¿Estás seguro de eliminar este usuario?');">
                                            <input type="hidden" name="accion" value="eliminar">
                                            <input type="hidden" name="idUsuario" value="<%= usuario.getId()%>">
                                            <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                                        </form>
                                    </td>

                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="4" class="text-center">No hay usuarios registrados.</td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="scripts.js"></script>
    </body>
</html>