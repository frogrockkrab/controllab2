/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class View extends javax.swing.JFrame {

    static JButton jb[] = new JButton[40];
    static JLabel jt[] = new JLabel[40];
    JPanel jp[] = new JPanel[40];
    Function b = new Function();
    int k = 0, l = 0;
    String ip[] = new String[40];
    static String defaultgateway;
    static ImageIcon on = new ImageIcon("src/IMG/on.png");
    static ImageIcon off = new ImageIcon("src/IMG/off.png");

    /**
     * Creates new form View
     */
    public View() {
        initComponents();

        IPList();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                jb[k] = new JButton();
                jt[k] = new JLabel(ip[k]);
                jp[k] = new JPanel();
                jp[k].setBackground(Color.yellow);
                jp[k].add(jb[k]);
                jp[k].add(jt[k]);
                jPanel1.add(jp[k]);
                k++;
            }
        }

    }

    private void IPList() {
        Connection connection = getconnect();
        String sql = " SELECT * FROM  ip";
        PreparedStatement pre;
        ResultSet rs;
        int i = 0;
        try {
            pre = connection.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                if (i < 40) {
                    ip[i] = rs.getString("IP_Address");
                } else {
                    defaultgateway = rs.getString("IP_Address");
                }
                i++;
            }
        } catch (Exception ex) {
            Logger.getLogger(Editcourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getconnect() {
        Connection con;
        String url = "jdbc:mysql://localhost/controllab";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "");
            return con;
        } catch (Exception ex) {
            Logger.getLogger(Editcourse.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        addstudent = new javax.swing.JButton();
        addcourse = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 51, 102));
        jPanel1.setLayout(new java.awt.GridLayout(5, 8, 2, 1));

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        addstudent.setText("Edit student");
        addstudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addstudentActionPerformed(evt);
            }
        });

        addcourse.setText("Edit Course");
        addcourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addcourseActionPerformed(evt);
            }
        });

        jButton1.setText("Internet");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Ban list");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Edit IP");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jToggleButton1.setText("block");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(addstudent)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addcourse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(addstudent)
                .addGap(18, 18, 18)
                .addComponent(addcourse)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 230, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addstudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addstudentActionPerformed
        b.editstudent();
    }//GEN-LAST:event_addstudentActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Socket send;
        PrintWriter out;
        IPList();
        for (int i = 0; i < 40; i++) {
            System.out.println(Connect.onoff[i]);
            if (Connect.onoff[i].equals("online") || Connect.onoff[i].equals("wait")) {
                System.out.println(ip[i]);
                try {
                    send = new Socket(ip[i], 25005);
                    out = new PrintWriter(send.getOutputStream());
                    out.println("blocknet");
                    out.flush();
                    out.println("111.111.111.111");
                    out.flush();
                    send.close();
                } catch (IOException ex) {
                    Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addcourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addcourseActionPerformed
        b.editcourse();
    }//GEN-LAST:event_addcourseActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        b.banlist();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Object[] options = {"Edit IP", "Set gateway"};
        int n = JOptionPane.showOptionDialog(null,
                "choose function",
                "choose command", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (n) {
            case 0:
                EditIP eip = new EditIP();
                eip.main();
                validate();
                break;
            case 1:
                String s = (String) JOptionPane.showInputDialog(
                        null, "Set Default Gateway", null,
                        JOptionPane.PLAIN_MESSAGE, null, null, defaultgateway);
                if ((s != null) && (s.length() > 0)) {
                    defaultgateway = s;
                    String query = "UPDATE `ip` SET `IP_Address`= '" + s + "' WHERE `Number` = '41'";
                    Query(query, "Update");
                }
                break;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        String gateway;
        try {
            InetAddress a = InetAddress.getByName("192.168.1.255");
            DatagramSocket s = new DatagramSocket();
            if (jToggleButton1.isSelected()) {
                gateway = "192.168.0.0#";
            } else {
                gateway = defaultgateway + "#";
            }
            DatagramPacket p = new DatagramPacket(gateway.getBytes(), gateway.getBytes().length, a, 8888);
            s.send(p);
        } catch (Exception ex) {
        }

    }//GEN-LAST:event_jToggleButton1ActionPerformed

    public void Query(String query, String message) {
        Connection con = getconnect();
        Statement st;
        try {
            st = con.createStatement();
            if (st.executeUpdate(query) == 1) {
                JOptionPane.showMessageDialog(null, "Data " + message + " Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Data " + message + " failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Login a = new Login();
        View v = new View();
        a.setModal(true);
        a.setVisible(true);
        if (a.confirm) {
            v.setVisible(true);
            Connect connect = new Connect();
            connect.createserver();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addcourse;
    private javax.swing.JButton addstudent;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
