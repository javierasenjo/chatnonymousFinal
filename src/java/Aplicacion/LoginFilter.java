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
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author ebenz
 */
public class LoginFilter implements Filter {
     protected FilterConfig filterConfig;
     // Se llama cuando se crea una instancia del filtro. 
     // Si se correlaciona con j_security_check, se llama 
     // la primera vez que se invoca j_security_check.
     @Override
     public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        }
     @Override
     public void destroy() {
        this.filterConfig = null;
        }
      // Se llama por cada solicitud correlacionada a este filtro. 
     // Si se correlaciona con j_security_check, 
     // se llama para cada acción j_security_check
     @Override
     public void doFilter(ServletRequest request, 
     ServletResponse response, FilterChain chain) 
         throws java.io.IOException, ServletException   {
         // efectuar aquí la acción previa al inicio de sesión
         chain.doFilter(request, response);  
         // llama al siguiente filtro de la cadena.  
         // j_security_check si este filtro 
         // se correlaciona con j_security_check.
        // efectuar aquí la acción posterior al inicio de sesión.
                 }
       }