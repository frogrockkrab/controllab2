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
public class Offender {
    String Discription,Time,path;

    public Offender(String Discription, String Time, String path) {
        this.Discription = Discription;
        this.Time = Time;
        this.path = path;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String Discription) {
        this.Discription = Discription;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
}
