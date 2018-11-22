/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 *
 * @author chuki
 */
public class UploadServlet extends HttpServlet {
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50*1024;
    private int maxMemSize = 4*1024;
    private File file;
    
    public void init(){
    filePath= getServletContext().getInitParameter("file-upload");
    
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

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
                ServletContext aplicacion = getServletContext();
        ArrayList <Object> lista_mensajes = (ArrayList) aplicacion.getAttribute("lista_mensajes");
       isMultipart = ServletFileUpload.isMultipartContent(request);
       response.setContentType("text/html");
       PrintWriter out = response.getWriter();
       
       if (!isMultipart){
             out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
         out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
       }
       DiskFileItemFactory factory =  new DiskFileItemFactory();
       factory.setSizeThreshold(maxMemSize);
       factory.setRepository(new File("/home/chuki/glassfish-4.1.1/glassfish/domains/domain1/applications/__internal/Chatnonymous/images"));
       ServletFileUpload upload = new ServletFileUpload(factory);
       upload.setSizeMax(maxFileSize);
       try{
       List fileItems = upload.parseRequest(request);
       Iterator i = fileItems.iterator();
       
       
         
         while(i.hasNext()){
         FileItem fi = (FileItem)i.next();
         if(!fi.isFormField()){
         String fieldName = fi.getFieldName();
         String fileName = fi.getName();
         String contentType = fi.getContentType();
         boolean isInMemory = fi.isInMemory();
         long sizeInBytes=fi.getSize();
         if(fileName.lastIndexOf("\\") >= 0 ){
         file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
               } else {
                  file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
               }
               fi.write( file ) ;
               out.println("Uploaded Filename: " + fileName + "<br>");
               System.out.println(fileName);
         }
         }
         lista_mensajes.add(file);
         
            RequestDispatcher rd = getServletContext().getNamedDispatcher("ChatDisplay");
      rd.forward(request, response);
         }
        
         catch(Exception ex){
         System.out.println(ex);
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
