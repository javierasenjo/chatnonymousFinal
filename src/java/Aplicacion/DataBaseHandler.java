/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author javie
 */
public class DataBaseHandler {

    public ResultSet loginear(String username, String password) throws SQLException {
        ResultSet rs = null;
        try {
            InitialContext initialcontext = new InitialContext();
            DataSource datasource;
            datasource = (DataSource) initialcontext.lookup("jdbc/swDatabase");
            Connection conn = datasource.getConnection();
            String query = "select * from tb_users where email ='" + username + "' and password ='" + password + "';";
            Statement st;
            st = conn.createStatement();
            rs = st.executeQuery(query);
            conn.close();
        } catch (NamingException ex) {
            Logger.getLogger(LoginChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet register(String username, String password) throws SQLException, NamingException {
        ResultSet rs = null;
        try {
            InitialContext initialcontext = new InitialContext();
            DataSource datasource;
            datasource = (DataSource) initialcontext.lookup("jdbc/swDatabase");
            Connection conn = datasource.getConnection();
            String query = "select * from tb_users where email ='" + username + "';";
            Statement st;
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if (!rs.next()) {
                username = "'" + username + "'";
                String contrasena = "'" + password + "'";
                query = "insert into tb_users values (" + username + ",sha1('" + contrasena + "'));";

                st = conn.createStatement();
                st.execute(query);
                conn.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(LoginChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void recuperarContrasena(String username, String password) throws SQLException {
        try {
            InitialContext initialcontext = new InitialContext();
            DataSource datasource;
            datasource = (DataSource) initialcontext.lookup("jdbc/swDatabase");
            Connection conn = datasource.getConnection();
            String query = "update tb_users set password =sha1('" + password + "') where email='" + username + "';";
            Statement st;
            st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
        } catch (NamingException ex) {
            Logger.getLogger(LoginChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static String hash(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
        msdDigest.update(pass.getBytes("UTF-8"), 0, pass.length());
        pass = DatatypeConverter.printHexBinary(msdDigest.digest());
        return pass;
    }
     
      public static void createTables() {
        try {
             Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbxTesting","root","root");
            Statement st = conn.createStatement();
            int myResult = st.executeUpdate("CREATE TABLE eventos(evento_id int(11) NOT NULL AUTO_INCREMENT,nombre varchar(30) DEFAULT NULL,direccion varchar(60) DEFAULT NULL,ciudad varchar(50) DEFAULT NULL,fecha varchar(20) DEFAULT NULL,listas int(11) DEFAULT NULL,PRIMARY KEY (evento_id))");
            System.out.println("Table created !");
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void createDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=root");
            Statement st = conn.createStatement();
            int myResult = st.executeUpdate("CREATE DATABASE IF NOT EXISTS dbxTesting");
            System.out.println("Database created !");
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

     
     
}
