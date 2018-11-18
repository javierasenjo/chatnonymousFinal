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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author chuki
 */
public class RegisterChat extends HttpServlet {

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
            throws ServletException, IOException, NamingException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String usuario = request.getParameter("username");
            String contrasena1=request.getParameter("password");
            String contrasena2 = request.getParameter("password2");
            
            if(contrasena1.equals(contrasena2)){
            InitialContext initialcontext = new InitialContext();
              DataSource datasource;
              datasource = (DataSource) initialcontext.lookup("jdbc/swDatabase");
              Connection conn = datasource.getConnection();
              String posible_usuario = request.getParameter("username");
              String posible_password= request.getParameter("password");
              String query = "select * from tb_users where email ='"+ posible_usuario + "';";
              Statement st;
              st = conn.createStatement();
              ResultSet rs = st.executeQuery(query);
              if (!rs.next()){
               usuario = "'"+posible_usuario+"'";
               String contrasena = "'"+posible_password+"'";
              query = "insert into tb_users values ("+usuario+","+contrasena+");";
              st.execute(query);
              conn.close();
              RequestDispatcher rd = getServletContext().getNamedDispatcher("LoginDisplay");
              rd.forward(request, response);
              
              }
            
            }
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
        } catch (NamingException ex) {
            Logger.getLogger(RegisterChat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterChat.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NamingException ex) {
            Logger.getLogger(RegisterChat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterChat.class.getName()).log(Level.SEVERE, null, ex);
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
