/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class Student {

    public ServerSocket ss;
    public Socket r, s;
    public BufferedReader read;
    PrintWriter out;
    public String msg;
    static int port = 25000;

    public void study() {
        con();
        Reciver.start();
        
    }
    Thread Reciver = new Thread(new Runnable() {
        @Override
        public void run() {
            String msg;
            try {
                ss = new ServerSocket(25005);
                while (true) {
                    r = ss.accept();
                    read = new BufferedReader(new InputStreamReader(r.getInputStream()));
                    msg = read.readLine();
                    System.out.println(msg);
                    if (msg.equals("Shutdown")) {
                        System.out.println("Test");
                    } else if (msg.equals("Restart")) {
                        System.out.println("Test");
                    } else {
                        port = Integer.parseInt(msg);
                        recon();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }/* finally {
                try {
                    ss.close();
                    System.out.println("Socket close");
                } catch (IOException ex) {
                    System.out.println("Error " + ex);
                }
            }*/
        }
    });

    Thread Sender = new Thread(new Runnable() {
        Timer myTimer = new Timer();

        @Override
        public void run() {
            /*myTimer.schedule(new TimerTask() {
                public void run() {
                    try {
                        s = new Socket("localhost", port);
                        PrintWriter out = new PrintWriter(s.getOutputStream());
                        out.println("online");
                        out.flush();
                        s.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, 0, 10000);*/
        }

    });

    public void con() {
        try {
            s = new Socket("localhost", port);
            s.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
            "Server Down","Inane error",
            JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void recon() {
        Sender.start();
    }

}
