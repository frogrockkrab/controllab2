/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


/**
 *
 * @author admin
 */
public class Function {

    /*Connection con;
    JFrame newFrame = new JFrame("New Window");

    /*public Connection ConnectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/controllab";
            con = DriverManager.getConnection(url, "root", "");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public void addstudent(){
        Ad a = new Ad();
        a.main();
    }

    public void editstudent() {

    }

    public void editcourse() {
        Editcourse ec = new Editcourse();
        ec.main();
    }

}
