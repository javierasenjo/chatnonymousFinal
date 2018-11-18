package Aplicacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
String u = "chuki";
String c= "chuki";
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
            throws ServletException, IOException, SQLException {
    try {
        InitialContext initialcontext = new InitialContext();
        DataSource datasource;
        datasource = (DataSource) initialcontext.lookup("jdbc/swDatabase");
        Connection conn = datasource.getConnection();
        ServletContext aplicacion = getServletContext();
        int contador = (int) aplicacion.getAttribute("contador_mensajes");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        String usuario = request.getParameter("username");
        String contrasena = request.getParameter("password");
        String query = "select * from tb_users where email ='"+ usuario + "' and password ='" + contrasena+"';";
        Statement st;
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next())
        {
            
            ArrayList <String> lista_mensajes = new ArrayList();
            sesion.setAttribute("contador", contador);
            RequestDispatcher rd = getServletContext().getNamedDispatcher("ChatDisplay");
            rd.forward(request, response);
            
            
        }
        else
        {
            RequestDispatcher rd = getServletContext().getNamedDispatcher("LoginDisplay");
            rd.forward(request, response);
        }
    } catch (NamingException ex) {
        Logger.getLogger(LoginChat.class.getName()).log(Level.SEVERE, null, ex);
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
