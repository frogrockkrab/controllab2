/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class Connect {

    public static ServerSocket ss;
    public static Socket sock;
    static String ip;
    String[] ipclient = new String[80];
    String[] ipall = new String[80];
    ArrayList<Integer> list = new ArrayList<Integer>();
    int clientnumber;

    public Connect() {
        for (int i = 0; i < 40; i++) {
            if (i == 39) {
                ipclient[39] = "127.0.0.1";
            } else {
                ipclient[i] = "1";
            }
        }
        for (int i = 0; i < 40; i++) {
            list.add(new Integer(i + 1));
        }
        Collections.shuffle(list);
    }

    public void con() {
        try {
            ss = new ServerSocket(25000);
            while (true) {
                sock = ss.accept();
                //ipall[clientnumber] = getip();
                clientnumber = button();
                new Thread(new Recive(list.get(clientnumber), clientnumber)).start();
                new Thread(new Send(ipall[clientnumber], clientnumber, list.get(clientnumber))).start();
            }
        } catch (IOException ex) {
            /*try {
                ss.close();
                System.out.println("socket close");
            } catch (IOException ex1) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex1);
            }*/
        }
    }

    public static String getip() {
        String socket = sock.getRemoteSocketAddress().toString().substring(1);
        String[] address = socket.split(":");
        ip = address[0];
        return ip;
    }

    public void setport() {

    }

    public int button() {
        int i;
        for (i = 0; i < 40; i++) {
            if (getip().equals(ipclient[i])) {
                System.out.println(i);
                View.jb[i].setEnabled(true);
                View.jb[i].setIcon(View.on);
                break;
            }
        }
        return i;
    }

}

class Send implements Runnable {

    String ip;
    int clientnumber;
    Socket send;
    int port;
    PrintWriter out;

    Send(String ip, int clientnumber, int port) {
        this.ip = ip;
        this.port = port + 25000;
        this.clientnumber = clientnumber;

    }

    @Override
    public void run() {
        try {
            send = new Socket(ip, 25005);
            out = new PrintWriter(send.getOutputStream());
            System.out.println("Random port = " + port + " " + clientnumber);
            out.println(port);
            out.flush();
            send.close();
            addoption();
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addoption() {
        try {
            send = new Socket(ip, 25005);
            out = new PrintWriter(send.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
        View.jb[clientnumber].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object[] options = {"Shutdown", "Restart", "Check"};
                int n = JOptionPane.showOptionDialog(null,
                        "Command to client number " + clientnumber,
                        "choose command", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                switch (n) {
                    case 0:
                        System.out.println("Shutdown");
                        out.println("Shutdown");
                        out.flush();
                         {
                            try {
                                send.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case 1:
                        System.out.println("Restart");
                        out.println("Restart");
                        out.flush();
                         {
                            try {
                                send.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Check");
                        
                        break;
                    default:
                        break;
                }
            }
        });
    }

}

class Recive implements Runnable {

    ServerSocket newss;
    String msg;
    Socket recive;
    int clientnumber, port;
    public static BufferedReader read;

    public int count = 1;

    Recive(int port, int clientnumber) {
        this.port = port + 25000;
        this.clientnumber = clientnumber;
    }

    @Override
    public void run() {
        try {
            newss = new ServerSocket(port);
            Timer.start();
            while (true) {
                //msg = read.readLine();
            }
        } catch (IOException ex) {

        }

    }
    Thread Timer = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    count++;
                    Timer.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Recive.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });

}
