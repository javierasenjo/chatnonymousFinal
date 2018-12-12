package Aplicacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chuki
 */
public class ChatDisplay extends HttpServlet {

    String x = "";

    //File file = new File(x);
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
        DataBaseHandler bd = new DataBaseHandler();
        HttpSession sesion = request.getSession();
        String usuario = (String) request.getSession().getAttribute("usuario");

        ResultSet rs = bd.comprobaciónUsuarioExiste(usuario);

        if (rs.next()) {
            ServletContext aplicacion = getServletContext();
            ArrayList<Object> lista_mensajes = (ArrayList) aplicacion.getAttribute("lista_mensajes");

            try {
                int contador = (int) sesion.getAttribute("contador");
            } catch (Exception e) {
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/index.xhtml");
                rd.forward(request, response);
            }
            int posicion = (int) aplicacion.getAttribute("contador_mensajes");
        
//        Enumeration<String> attributes = request.getSession().getAttributeNames();
//        while (attributes.hasMoreElements()) {
//            String attribute = attributes.nextElement();
//            System.out.println(attribute + " : " + request.getSession().getAttribute(attribute));
//        }
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/chatDisplay.xhtml");
            rd.forward(request, response);
        } else {
            System.out.println("Estas expulsado");
            sesion.invalidate();
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.xhtml");
            rd.forward(request, response);
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
            Logger.getLogger(ChatDisplay.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ChatDisplay.class.getName()).log(Level.SEVERE, null, ex);
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
