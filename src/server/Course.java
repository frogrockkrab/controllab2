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
public class Course {
    String Subject;
    int Section;

    public Course(String Subject, int Section) {
        this.Subject = Subject;
        this.Section = Section;
    }

    public int getSection() {
        return Section;
    }

    public void setSection(int Section) {
        this.Section = Section;
    }    

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    
}
