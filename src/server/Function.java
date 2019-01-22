/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class Function {

    Connection con;
    JFrame newFrame = new JFrame("New Window");

    public Connection ConnectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/controllab";
            con = DriverManager.getConnection(url, "root", "");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addstudent() throws SQLException {
        Addstudent a = new Addstudent();
        a.main();
    }

    public void deletestudent() {

    }

    public void editstudent() {

    }

    public void addcourse() {

    }

    public void deletecourse() {

    }

    public void editcourse() {

    }

}
