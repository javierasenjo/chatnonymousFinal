package Aplicacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author chuki
 */
public class LoginChat extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NoSuchAlgorithmException {
        try{
        ServletContext aplicacion = getServletContext();
        int contador = (int) aplicacion.getAttribute("contador_mensajes");
        ArrayList<String> viewers_conectados = (ArrayList) aplicacion.getAttribute("viewers_conectados");
        ArrayList<String> usuarios_conectados = (ArrayList) aplicacion.getAttribute("usuarios_conectados");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        String usuario = request.getParameter("username");
        String contrasena = request.getParameter("password");
        DataBaseHandler bd = new DataBaseHandler();
        String contrasena_cifrada = bd.hash(contrasena).toLowerCase();
        //Para forzar excepciones
        //Integer.parseInt("dewrftgbyhnu");
        ResultSet rs = bd.loginear(usuario, contrasena_cifrada);
        EmailValidator validador = new EmailValidator();

        //admin
        if (usuario.equalsIgnoreCase("admin") && rs.next()) {
            RequestDispatcher rd = getServletContext().getNamedDispatcher("ChatServletAdmin");
            Cookie[] listaCookies = request.getCookies();
            Cookie galletaSelectora = null;
            //Cookie admin =null;
            //admin.setValue("admin");
            if (listaCookies != null) {
                for (Cookie galleta : listaCookies) {
                    if (galleta.getName().equals("Contador")) {
                        galletaSelectora = galleta;
                        galletaSelectora.setValue("0");
                    }
                }
            }
            if (galletaSelectora == null) {
                galletaSelectora = new Cookie("Contador", "1");
            }
          String admin = null;
                   request.getSession().setAttribute("admin", admin);
            response.addCookie(galletaSelectora);
            request.getSession().setAttribute("contador", contador);
           // request.getSession().setAttribute("usuario", usuario);
            rd.forward(request, response);
            //usuario normal
        } else if (rs.next()) {
            //usuario ceu
            if (validador.validadorCeu(usuario)) {
                ArrayList<String> lista_mensajes = new ArrayList();
                request.getSession().setAttribute("usuario", usuario);
                usuarios_conectados.add(usuario);
                aplicacion.setAttribute("usuarios_conectados", usuarios_conectados);
                System.out.println("Se ha añadido a la lista de conectados:" + usuario);
                sesion.setAttribute("contador", contador);
                String usr= null;
                 request.getSession().setAttribute("usuario",usr);
                Cookie[] listaCookies = request.getCookies();
                Cookie galletaSelectora = null;

                if (listaCookies != null) {
                    for (Cookie galleta : listaCookies) {
                        if (galleta.getName().equals("Contador")) {
                            galletaSelectora = galleta;
                            galletaSelectora.setValue(Integer.toString(Integer.parseInt(galleta.getValue()) + 1));
                        }
                    }
                }
                if (galletaSelectora == null) {
                    galletaSelectora = new Cookie("Contador", "1");
                }
                response.addCookie(galletaSelectora);
                request.getSession().setAttribute("usuario", usuario);
                request.getSession().setAttribute("contador", contador);
                RequestDispatcher rd = getServletContext().getNamedDispatcher("ChatServlet");
                rd.forward(request, response);
                //usuario resto correos
            } else {
                //request.getSession().setAttribute("usuario", usuario);
                viewers_conectados.add(usuario);
//                for (int i = 0; i < viewers_conectados.size(); i++) {
//                    System.out.println(i);
//                }
                aplicacion.setAttribute("viewers_conectados", viewers_conectados);
                request.getSession().setAttribute("contador", contador);

                ArrayList<Object> lista_mensajes = (ArrayList<Object>) aplicacion.getAttribute("lista_mensajes");
                aplicacion.setAttribute("contador_mensajes", (int) aplicacion.getAttribute("contador_mensajes") + 1);
                System.out.println(lista_mensajes);
                lista_mensajes.add("Se ha conectado al chat: " + usuario);
                System.out.println(lista_mensajes);
                aplicacion.setAttribute("lista_mensajes", lista_mensajes);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/chatDisplayNoLoggeado.xhtml");
                rd.forward(request, response);
            }
        } else {
            System.out.println("correo mal");
            RequestDispatcher rd = getServletContext().getNamedDispatcher("LoginDisplay");
            rd.forward(request, response);
        }
    }catch (Exception Ex){
            System.out.println(Ex);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.xhtml");
        rd.forward(request,response);
    }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(LoginChat.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(LoginChat.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
