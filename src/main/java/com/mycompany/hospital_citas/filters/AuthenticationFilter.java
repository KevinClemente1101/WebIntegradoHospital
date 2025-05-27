package com.mycompany.hospital_citas.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String uri = httpRequest.getRequestURI();
        boolean isLogin = uri.endsWith("/login") || uri.endsWith("/login.jsp");
        boolean isRegistro = uri.endsWith("/registro") || uri.endsWith("/registro.jsp");
        boolean isVerificarCorreo = uri.endsWith("/verificar-correo") || uri.endsWith("/verificar_codigo.jsp");
        boolean isValidarCodigo = uri.endsWith("/validar-codigo");
        boolean isIndex = uri.endsWith("/index.jsp") || uri.endsWith("/index") || uri.equals(httpRequest.getContextPath() + "/");
        boolean isStatic = uri.contains("/assets/") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith(".ico");
        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);

        if (isLoggedIn || isLogin || isRegistro || isVerificarCorreo || isValidarCodigo || isIndex || isStatic) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
} 