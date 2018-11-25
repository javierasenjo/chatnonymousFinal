package Aplicacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
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
            throws ServletException, IOException {

        ServletContext aplicacion = getServletContext();
        ArrayList<Object> lista_mensajes = (ArrayList) aplicacion.getAttribute("lista_mensajes");
        HttpSession sesion = request.getSession();
        int contador = (int) sesion.getAttribute("contador");
        int posicion = (int) aplicacion.getAttribute("contador_mensajes");
        response.setContentType("text/html;charset=UTF-8");
        int numeroSesion = 1;
        Cookie[] listaCookies = request.getCookies();

        for (Cookie galleta : listaCookies) {
            if (galleta.getName().equals("Contador")) {
                numeroSesion = Integer.parseInt(galleta.getValue());
            }
        }
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<title>ChatServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>mensajes</h1>");

        //for (String a:lista_mensajes){
        //out.println("<p>"+a+"</p>");
        //}
        System.out.println(lista_mensajes);

        for (int i = contador; i < lista_mensajes.size(); i++) {
            if (lista_mensajes.get(i).getClass().equals(x.getClass())) {
                out.println(lista_mensajes.get(i));

            } else {
                File get = (File) lista_mensajes.get(i);
                System.out.println("Nombre:"+get.getName());
                out.println("<img src=" +".\\images\\"+get.getName() + "></img>");

            }

            //out.println(lista_mensajes.get(i));
            System.out.println(lista_mensajes.get(i).getClass());
//            if(lista_mensajes.get(i).getClass().equals(x.getClass())){
//            out.println("String");
//            
//            }
//            if(lista_mensajes.get(i).getClass().equals(file.getClass())){
//            out.println( "<img src=>" );
//            
//            }
//            
            out.println("</br>");
        }

        out.println("<form name='loginForm' method='post' action='ChatServlet'>");
        out.println("Mensaje<input type='text' name='message'/><br/>");

        out.println("<input type='submit' value='Send'/>");
        out.println("</form>");
        out.println("<h3>Enviar archivo </h3>");
        out.print("<form action ='UploadServlet' method ='post' enctype ='multipart/form-data'>");
        out.println("<input type='file' name='file' size='50'/>");
        out.print("<input type='submit' value ='Subir archivo'/>");
        out.println("</form>");
        out.print("<form action ='ViewersServlet' method ='post'>");
        out.print("<input type='submit' value ='Ver los observadores'/>");
        out.println("</form>");
        out.println("<h3>Has visitado esta página " + numeroSesion + " veces </H3>");
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

}
