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
            System.out.println("hello");
            try {
                ss = new ServerSocket(25005);
                while (true) {
                    r = ss.accept();
                    read = new BufferedReader(new InputStreamReader(r.getInputStream()));
                    msg = read.readLine();
                    switch (msg) {
                        case "Shutdown":
                            System.out.println(msg);
                            //Run shutdown
                            break;
                        case "Restart":
                            System.out.println(msg);
                            //Run Restart
                            break;
                        case "success":
                            JOptionPane.showMessageDialog(null, "login Successfully");
                            StudentLogin.a();
                            break;
                        case "failed":
                            JOptionPane.showMessageDialog(null, "login faield");
                            break;
                        case "blocknet":
                            System.out.println(msg);
                            msg = read.readLine();
                            System.out.println(msg);
                            break;
                        case "unblocknet":
                            System.out.println(msg);
                            msg = read.readLine();
                            System.out.println(msg);
                            break;
                        default:
                            port = Integer.parseInt(msg);
                            recon();
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    Thread Sender = new Thread(new Runnable() {
        Timer myTimer = new Timer();

        @Override
        public void run() {

            myTimer.schedule(new TimerTask() {

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
            }, 0, 5000);
        }
    });

    public void con() {
        try {
            s = new Socket("localhost", port);
            s.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Server Down", "Inane error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void recon() {
        Sender.start();
    }

    public void send(String msg) {
        try {
            s = new Socket("localhost", port);
            PrintWriter out1 = new PrintWriter(s.getOutputStream());
            out1.println(msg);
            out1.flush();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
