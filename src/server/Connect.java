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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
    static String[] onoff = new String[40];
    ArrayList<Integer> list = new ArrayList<Integer>();
    int clientnumber;
    static Thread Send[] = new Thread[40];
    static Thread Recive[] = new Thread[40];
    int j = 0;

    public Connect() {
        System.out.println("hello");
        for (int i = 0; i < 40; i++) {
            if (i == 39) {
                ipclient[39] = "127.0.0.1";
            } else {
                ipclient[i] = "1";
            }
            onoff[i] = "offline";
        }
        for (int i = 0; i < 40; i++) {
            list.add(new Integer(i + 1));
        }
        Collections.shuffle(list);
    }

    public void createserver() {
        System.out.println("open");
        try {
            ss = new ServerSocket(25000);
            System.out.println("open port");
            while (true) {
                sock = ss.accept();
                clientnumber = button();
                Send[j] = new Thread(new Recive(list.get(clientnumber), clientnumber));
                Recive[j] = new Thread(new Send(ipall[clientnumber], clientnumber, list.get(clientnumber)));
                Send[j].start();
                Recive[j].start();
                j++;
                /*new Thread(new Recive(list.get(clientnumber), clientnumber)).start();
                new Thread(new Send(ipall[clientnumber], clientnumber, list.get(clientnumber))).start();*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getip() {
        String socket = sock.getRemoteSocketAddress().toString().substring(1);
        String[] address = socket.split(":");
        ip = address[0];
        return ip;
    }

    public int button() {
        int i;
        for (i = 0; i < 40; i++) {
            if (getip().equals(ipclient[i])) {
                System.out.println(i);
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

        View.jb[clientnumber].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object[] options = {"Shutdown", "Restart", "Check"};
                int n = JOptionPane.showOptionDialog(null,
                        "Command to client number " + clientnumber,
                        "choose command", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                try {
                    send = new Socket(ip, 25005);
                    out = new PrintWriter(send.getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    String msg, clientid;
    Socket recive;
    int clientnumber, port;
    public BufferedReader read;
    Connection con = null;
    String url = "jdbc:mysql://localhost/controllab";

    public int count = 1;

    Recive(int port, int clientnumber) {
        this.port = port + 25000;
        this.clientnumber = clientnumber;
    }

    @Override
    public void run() {
        try {
            newss = new ServerSocket(port);
            while (true) {
                recive = newss.accept();
                read = new BufferedReader(new InputStreamReader(recive.getInputStream()));
                PrintWriter out = new PrintWriter(recive.getOutputStream());
                if ((msg = read.readLine()).equals("online")) {
                    Connect.onoff[clientnumber] = "online";
                } else if ((msg = read.readLine()).equals("login")) {
                    String user, pass;
                    user = read.readLine();
                    pass = read.readLine();
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(url, "root", "");
                        String sql = " SELECT * FROM  student "
                                + " WHERE St_Username = ? "
                                + " AND St_Password = ? ";
                        PreparedStatement pre = con.prepareStatement(sql);
                        pre.setString(1, user);
                        pre.setString(2, pass);

                        ResultSet rec = pre.executeQuery();

                        if (rec.next()) {
                            out.println("success");
                            clientid = user;

                        } else {
                            out.println("failed");
                        }
                        out.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException ex) {
            Connect.Send[39].stop();
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

    TimerTask task = new TimerTask() {
        public void run() {
            count++;
        }
    };

    /*Timer timer = new Timer("Timer");
    timer.schedule(task, 0, 1000);*/
}
