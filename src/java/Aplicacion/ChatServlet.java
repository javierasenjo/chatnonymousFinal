package Aplicacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class ChatServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String mensaje = request.getParameter("message");
        ServletContext aplicacion = getServletContext();
        String usuario = (String) request.getSession().getAttribute("usuario");
        ArrayList<Object> lista_mensajes = (ArrayList<Object>) aplicacion.getAttribute("lista_mensajes");
        ArrayList<Object> lista_mensajes_admin = (ArrayList<Object>) aplicacion.getAttribute("lista_mensajes_admin");
        
        if (request.getParameter("message") == null || request.getParameter("message").equalsIgnoreCase("")) {
            RequestDispatcher rd = getServletContext().getNamedDispatcher("ChatDisplay");
            rd.forward(request, response);
        } else {
            aplicacion.setAttribute("contador_mensajes", (int) aplicacion.getAttribute("contador_mensajes") + 1);

            lista_mensajes.add(mensaje);
            lista_mensajes_admin.add(usuario + ": " + mensaje);
            System.out.println(usuario + ": " + mensaje);
            aplicacion.setAttribute("lista_mensajes", lista_mensajes);
            aplicacion.setAttribute("lista_mensajes_admin", lista_mensajes_admin);
//            for (int i = 0; i < lista_mensajes.size(); i++) {
//                System.out.println("lista normal" + lista_mensajes.get(i));
//            }
//            for (int i = 0; i < lista_mensajes_admin.size(); i++) {
//                System.out.println(lista_mensajes_admin.get(i));
//            }
            RequestDispatcher rd = getServletContext().getNamedDispatcher("ChatDisplay");
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    @Override
    public void destroy() {
        getServletContext().setAttribute("lista_mensajes", null);
        getServletContext().setAttribute("lista_mensajes_admin", null);
        System.out.println("Destructor");
        System.out.println(getServletContext().getAttribute("lista_mensajes"));
    }
}
