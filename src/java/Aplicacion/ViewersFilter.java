/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import java.io.IOException;
import java.util.ArrayList;
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

/**
 *
 * @author fer
 */
@WebFilter(filterName = "ViewersFilter", urlPatterns = {"/ViewersServlet"})

public class ViewersFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("[Filtro viewers] Procesando Petición");
        try {
            ServletContext contexto= request.getServletContext();
            ArrayList usuarios_conectados = (ArrayList) contexto.getAttribute("usuarios_conectados");
            if (usuarios_conectados.isEmpty()) {
                System.out.println("[Filtro viewers] Redirigiendo a viewersDisplayEmpty");
                RequestDispatcher requestDispatcher = contexto.getRequestDispatcher("/viewersDisplayEmpty.xhtml");
                requestDispatcher.forward(request, response);

            } else {
                //Redirigir
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
