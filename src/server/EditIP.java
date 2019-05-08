/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author admin
 */
public class EditIP extends javax.swing.JFrame {

    /**
     * Creates new form EditIP
     */
    int index;

    public EditIP() {
        initComponents();
        Showdata();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("IP List"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Computer", "IP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int i = jTable1.getSelectedRow();
        TableModel t = jTable1.getModel();
        index = (int) t.getValueAt(i, 0);
        String ip = t.getValueAt(i, 1).toString();
        if (evt.getClickCount() == 2) {
            String s = (String) JOptionPane.showInputDialog(
                    null, "Input Program name", null,
                    JOptionPane.PLAIN_MESSAGE, null, null, ip);
            if ((s != null) && (s.length() > 0)) {
                String query = "UPDATE `ip` SET `IP_Address`='" + s + "' WHERE `Number` = '" + index + "'";
                Query(query, "Update");
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked
    
    /**
     * @param args the command line arguments
     */
    public void main() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditIP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditIP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditIP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditIP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditIP().setVisible(true);
            }
        });
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

    public ArrayList<Ban> IPList() {
        ArrayList<Ban> iplist = new ArrayList<>();
        Connection connection = getconnect();
        String sql = " SELECT * FROM  ip WHERE `Number` != 41";
        PreparedStatement pre;
        ResultSet rs;
        try {
            pre = connection.prepareStatement(sql);
            rs = pre.executeQuery();
            Ban ip;
            while (rs.next()) {
                ip = new Ban(rs.getString("IP_Address"));
                iplist.add(ip);
            }
        } catch (Exception ex) {
            Logger.getLogger(Editcourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return iplist;
    }

    public void Showdata() {
        ArrayList<Ban> list = IPList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Object[] row = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            row[0] = i + 1;
            row[1] = list.get(i).getBan();
            model.addRow(row);
        }
    }

    public void Query(String query, String message) {
        Connection con = getconnect();
        Statement st;
        try {
            st = con.createStatement();
            if (st.executeUpdate(query) == 1) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                JOptionPane.showMessageDialog(null, "Data " + message + " Successfully");
                model.setRowCount(0);
                Showdata();
            } else {
                JOptionPane.showMessageDialog(null, "Data " + message + " failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
