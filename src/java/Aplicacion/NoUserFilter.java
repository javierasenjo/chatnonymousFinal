/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author javie
 */
@WebFilter(filterName = "NoUserFilter", urlPatterns = {"/ChatDisplay"})

public class NoUserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("[Filtro no usuario] Procesando Petición");
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession sesion = (HttpSession) httpRequest.getSession();
            EmailValidator validator = new EmailValidator();
            String user = (String) httpRequest.getSession().getAttribute("usuario");
            //System.out.println("El usuario es " + user);
            boolean correoGeneral = validator.validadorGeneral(user);
            boolean correoCeu = validator.validadorCeu(user);
            if (sesion == null) {
                System.out.println("[Filtro sesion] Redirigiendo a index.html");
                ServletContext servletContext = httpRequest.getSession().getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/index.xhtml");
                requestDispatcher.forward(request, response);

            } else if ("admin".equals(user)) {
                System.out.println("[Filtro sesion] Redirigiendo a chatAdminDisplay");
                ServletContext servletContext = httpRequest.getSession().getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getNamedDispatcher("ChatDisplayAdmin");
                requestDispatcher.forward(request, response);
            } else if (correoGeneral==true && correoCeu == false) {
                System.out.println("[Filtro sesion] Redirigiendo a chatDisplayNoLoggeado");
                ServletContext servletContext = httpRequest.getSession().getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getNamedDispatcher("ChatDisplayNoLoggeadoServlet");
                requestDispatcher.forward(request, response);
            } else {
                //Redirigir
                System.out.println("[Filtro sesion] No se mete por ningun sitio");
                chain.doFilter(request, response);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void destroy() {
    }
}
