<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Perfil Usuario"/>
</jsp:include>


                
<section class="py-5">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <jsp:include page="../WEB-INF/sidebar.jsp" />
            </div>
            <div class="col-lg-9">


                    <h2 class="mt-3 mt-lg-0">Mi Perfil Paciente</h2>
                    
                    <form action="actualizarPerfil" method="post">
                        <div class="mb-3">
                            <label class="form-label">Nombre</label>
                            <input type="text" class="form-control" name="nombre" value="${sessionScope.usuario.nombre}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Correo</label>
                            <input type="email" class="form-control" name="email" value="${sessionScope.usuario.email}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Contraseña</label>
                            <input type="password" class="form-control" name="password" placeholder="Nueva contraseña">
                        </div>
                        <button type="submit" class="btn btn-primary">Actualizar</button>
                    </form>




            </div>
        </div>
    </div>
</section>

<jsp:include page="../WEB-INF/footer.jsp"/>