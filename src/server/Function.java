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

    public void editstudent() {
        Editstudent es = new Editstudent();
        es.main();
    }

    public void editcourse() {
        Editcourse ec = new Editcourse();
        ec.main();
    }

}
