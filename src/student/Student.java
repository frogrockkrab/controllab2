/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
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
    static String Subject, Section;
    String host = "localhost";
    //String host = "25.13.78.14";

    public void study() {
        con();
        Reciver.start();

    }
    Thread Reciver = new Thread(new Runnable() {
        Process process = null;
        BufferedReader bufferRead = null;

        @Override
        public void run() {
            String msg;
            try {
                ss = new ServerSocket(25005);
                while (true) {
                    r = ss.accept();
                    read = new BufferedReader(new InputStreamReader(r.getInputStream()));
                    msg = read.readLine();
                    switch (msg) {
                        case "Shutdown":
                            System.out.println(msg);

                            try {
                                process = Runtime.getRuntime().exec("cmd /c D:\\Project\\Programs\\Project\\src\\batchfile\\shutdown.bat");
                                bufferRead = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                
                                String line = null;
                                while ((line = bufferRead.readLine()) != null) {
                                    System.out.println(line);
                                }
                                
                                if (process.exitValue() == 0) {
                                    System.out.println("Command start sucess...");
                                } else {
                                    System.out.println("Command start fail...");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    process.destroy();
                                    bufferRead.close();
                                } catch (IOException e) {
                                }
                            }
                            break;
                        case "Restart":
                            System.out.println(msg);
                            try {
                                process = Runtime.getRuntime().exec("cmd /c D:\\Project\\Programs\\Project\\src\\batchfile\\restart.bat");
                                bufferRead = new BufferedReader(new InputStreamReader(process.getInputStream()));

                                String line = null;
                                while ((line = bufferRead.readLine()) != null) {
                                    System.out.println(line);
                                }
                                
                                if (process.exitValue() == 0) {
                                    System.out.println("Command start sucess...");
                                } else {
                                    System.out.println("Command start fail...");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    process.destroy();
                                    bufferRead.close();
                                } catch (IOException e) {
                                }
                            }
                            break;
                        case "success":
                            JOptionPane.showMessageDialog(null, "login Successfully");
                            Subject = read.readLine();
                            System.out.println(Subject);
                            Section = read.readLine();
                            System.out.println(Section);
                            StudentLogin.a();
                            break;
                        case "failed":
                            JOptionPane.showMessageDialog(null, "login faield");
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

    Thread Boardcast = new Thread(new Runnable() {
        File file;
        FileOutputStream fos;
        DataOutputStream dos;
        Process process = null;
        BufferedReader bufferRead = null;

        @Override
        public void run() {
            try {
                byte[] b = new byte[256];
                DatagramSocket s = new DatagramSocket(8888);
                DatagramPacket p = new DatagramPacket(b, b.length);
                while (true) {
                    s.receive(p);
                    String m = new String(b, 0, b.length);
                    m = m.substring(0, m.indexOf('#'));
                    System.out.println(m);

                    file = new File("D:\\Project\\Programs\\Project\\src\\Blocknet\\block.bat");
                    //Create the file
                    if (file.createNewFile()) {
                        System.out.println("File is created!");
                    } else {
                        System.out.println("File already exists.");
                        file.delete();
                        file.createNewFile();
                    }

                    //Write Content
                    PrintWriter writer = new PrintWriter(file);
                    writer.println("@echo off");
                    writer.println("netsh interface ip set address \"Ethernet\" source = dhcp");
                    writer.println("ipconfig /renew");
                    writer.println("netsh interface ip set dns \"Ethernet\" source = dhcp");
                    writer.println("netsh interface ip show addresses");
                    writer.println("netsh interface ip set address \"Ethernet\" static 192.168.1.112 255.255.255.0 192.168.0.0 1");
                    writer.println("netsh interface ip add dns \"Ethernet\" 192.168.1.1");
                    writer.println("netsh interface ip add dns \"Ethernet\" 192.168.1.1 index=1");
                    writer.println("@pause");
                    writer.close();

                    Thread.sleep(2000);

                    try {
                        process = Runtime.getRuntime().exec("cmd /c D:\\Project\\Programs\\Project\\src\\Blocknet\\block.bat");
                        bufferRead = new BufferedReader(new InputStreamReader(process.getInputStream()));

                        String line = null;
                        while ((line = bufferRead.readLine()) != null) {
                            System.out.println(line);
                        }

                        if (process.exitValue() == 0) {
                            System.out.println("Command start sucess...");
                        } else {
                            System.out.println("Command start fail...");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            process.destroy();
                            bufferRead.close();
                        } catch (IOException e) {
                        }
                    }
                }
            } catch (Exception ex) {
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
                        s = new Socket(host, port);
                        PrintWriter out = new PrintWriter(s.getOutputStream());
                        out.println("online");
                        out.flush();
                        s.close();

                    } catch (IOException ex) {
                        Logger.getLogger(Student.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, 0, 5000);
        }
    });

    public void con() {
        try {
            s = new Socket(host, port);
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
        Boardcast.start();
    }

    public void send(String msg) {
        try {
            s = new Socket(host, port);
            PrintWriter out1 = new PrintWriter(s.getOutputStream());
            out1.println(msg);
            out1.flush();
            s.close();

        } catch (IOException ex) {
            Logger.getLogger(Student.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
