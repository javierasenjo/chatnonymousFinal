/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
                query = "insert into tb_users values (" + username + "," + contrasena + ");";

                st = conn.createStatement();
                st.execute(query);
                conn.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(LoginChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
