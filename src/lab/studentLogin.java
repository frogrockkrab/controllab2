package lab;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.sql.SQLException;


public class studentLogin extends javax.swing.JFrame 
{
    public static Socket socketoutput;
    public static Socket socketinput;
    public static Socket socketoutputAlert;
    public static ServerSocket server;
    
    public static int portInput = 26000;
    public static int portOutput = 25000;
    
    public static int portAlert = 27000;
    public static int portResetPWD = 28000;
    public static int portShutdown = 29000;
    public static int portbanweb = 30000;
    public static String IPServer = "192.168.121.238";
    
    public static String txtuser;
    public static String txtpwd;
    public static String txtcourse;
    public static String txtnameteacher;  
    public static String banweb; 
    public static String strbanweb;
    public static String txtnameteacher2;
    
    public studentLogin() 
    {
        initComponents();
        
        jTextField2.setText("");
        this.setExtendedState(this.MAXIMIZED_BOTH);
        
        Connection connect = null;
        Statement s = null;
        try 
        {
            
            Class.forName("com.mysql.jdbc.Driver"); //เชื่อมต่อ database โดยใช้ DriveManager
            connect = DriverManager.getConnection("jdbc:mysql://"+IPServer+":3306/project" +"?user=bee&password=some_pass");
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select distinct* from course");
            
                while(rs.next())
                {
                     String namecourse = rs.getString("Course"); //นำข้อมูลจาก database(Course) เก็บค่าใน namecourse เป็น string
                     jComboBox1.addItem(namecourse); //แสดงค่า namecourse ใน comboboxs
                }      
                
                      rs = stmt.executeQuery("select distinct* from teacher");
                while(rs.next())
                {
                     String nameteacher = rs.getString("NameTeacher");
                     jComboBox2.addItem(nameteacher);
                }   
                
            connect.close();                 
        }
        catch (Exception e) 
        {
//            JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
	}      
    }

    private static void sendAlert()
    {
        new java.util.Timer().schedule(new TimerTask() 
        {
            @Override
            public void run() 
            {
                Calendar time = Calendar.getInstance();
                SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss"); //รูปแบบการแสดงเวลา
                String formattedTime = formatTime.format(time.getTime());
                try
                {
                    socketoutputAlert = new Socket(IPServer,portAlert); //set IP Address : PORT Server
                    PrintWriter output = new PrintWriter(socketoutputAlert.getOutputStream()); // สร้างเส้นทางข้อมูลไปยัง Server
                    output.print(InetAddress.getLocalHost().getHostAddress()+"\n");
                    
                    System.out.println(formattedTime+" : "+InetAddress.getLocalHost().getHostAddress()); //แสดงเวลาและIP
                    output.flush();
                    socketoutputAlert.close();
                }
                catch(Exception ex){System.out.println("'Error sendAlert()รอserverเปิดการทำงาน "+ex);}
            }
	}, 3000, 3000);
        
        try
        {
            server  = new ServerSocket(portShutdown); //PORT 29000  ปิดคอม
            socketinput = server.accept();//ตอบรับการเชื่อมต่อ Network client
            BufferedReader readShutdown = new BufferedReader(new InputStreamReader(socketinput.getInputStream()));
            while(true)
            { 
                if(readShutdown.readLine().equals("Shutdown"))
                {
                    socketoutput = new Socket(IPServer,29001);                              //set IP Address : PORT shutdown 29000
                    PrintWriter output = new PrintWriter(socketoutput.getOutputStream());   // สร้างเส้นทางข้อมูลไปยัง Server                                        
                    output.print(txtuser+"\n");
                    output.print(txtcourse+"\n");
                    output.print(txtnameteacher+"\n");
                                    
                    output.flush();                                                             //Reset Object เพื่อรอรับข้อมูลจาก Server
                    output.close();
                    
                    System.out.println("yyyyyyy");
                    String cmds[] = new String[] {"cmd", "/c","Shutdown.bat"};
                    Runtime.getRuntime().exec(cmds);
                    
                }
                socketinput.close();
                socketinput = server.accept();
                readShutdown = new BufferedReader(new InputStreamReader(socketinput.getInputStream()));
            }
        }
        catch(Exception ex){System.out.println("'Error sendAlert() NO Shutdown' "+ex);}
    }
    
    private void btOK()
    {
        try    
        {
            socketoutput = new Socket(IPServer,25000);  //set IP Address : PORT Server 25000
            PrintWriter output = new PrintWriter(socketoutput.getOutputStream());  // สร้างเส้นทางข้อมูลไปยัง Server
            
            output.print("Send_UserPWD\n");//การส่งข้อมูลไปยังเครื่อง Server
            output.print(jTextField1.getText()+"\n");
            output.print(jTextField2.getText()+"\n");
            output.print(jComboBox1.getSelectedItem().toString()+"\n");
            output.print(jComboBox2.getSelectedItem().toString()+"\n");

   
            output.print(InetAddress.getLocalHost().getHostAddress()+"\n");//ส่งเลขไอพีเครื่องไปยัง Server
            output.flush(); //Reset Object เพื่อรอรับข้อมูลจาก Server
       
            
            try
            {
                server = new ServerSocket(portInput);//PORT 26000
                socketinput = server.accept(); //ตอบรับการเชื่อมต่อ Network client
                BufferedReader read = new BufferedReader(new InputStreamReader(socketinput.getInputStream())); //อ่านค่าจากข้อมูลให้เป็นข้อความ
                String st = read.readLine();
                System.out.println(st);
                
                switch( st )
                {
                    case "Accept":
                        
                        this.setVisible(false);//ปิด Dialog การทำงาน
                        
                         if(jTextField2.getText().equals("1234"))//รหัสเท่ากับ 1234 หรือไม่ 
                        {
                            while(true)
                            {
                                try
                                {              
                                    String NewPWD;
                                    do
                                    { 
                                        NewPWD = JOptionPane.showInputDialog("กรุณาเปลี่ยนรหัสเพื่อเข้าใช้งานในครั้งถัดไป (ถาวร)", "1234");
                                    }
                                    while( ("1234".equals(NewPWD)) );//ถ้า ChkPWD เท่ากับ 1234 ให้ใส่รหัสอีกครั้ง
                                    if(NewPWD!=null)
                                    {                   
                                        socketoutput = new Socket(IPServer,portResetPWD);  //ResetPWD PORT 28000    
                                        PrintWriter output1 = new PrintWriter(socketoutput.getOutputStream());  // สร้างเส้นทางข้อมูลไปยัง Server
                                        
                                        output1.print("ResetPWD\n");//การส่งข้อมูลไปยังเครื่อง Server
                                        output1.print(NewPWD+"\n");
                                        output1.print(jTextField1.getText()+"\n");
                                        output1.flush(); //Reset Object เพื่อรอรับข้อมูลจาก Server
                                        socketoutput.close(); //ปิด portoutput

                                        JOptionPane.showMessageDialog(null,"คุณเปลี่ยนพาสเวิร์ดเรียบร้อยแล้ว","ChangePassword",JOptionPane.PLAIN_MESSAGE);
                                        break;
                                    }
                                    JOptionPane.showMessageDialog(null,"กรุณาเปลี่ยนพาสเวิร์ด","Change Password",JOptionPane.PLAIN_MESSAGE);
                                }
                                catch (IOException | HeadlessException ex){System.out.println("'Error ResetPassword'");}
                            }
                            //==========================SertAlert===============================
                            sendAlert();//PORT 27000
                        }
                         else
                             {
                                txtuser = jTextField1.getText();
                                txtcourse = jComboBox1.getSelectedItem().toString();
                                txtnameteacher = jComboBox2.getSelectedItem().toString();
                                        
                                JOptionPane.showMessageDialog(null,"คุณเข้าใช้งานเรียบร้อยแล้ว","Welcome",JOptionPane.PLAIN_MESSAGE);
                                //==========================SertAlert===============================
                                 banweb();
                                sendAlert();//PORT 27000
                               
                            }
                    break;
                    default:
                        te.setVisible(false); 
                       JOptionPane.showMessageDialog(null,"Username หรือ Password ผิด!! กรุณากรอกใหม่อีกครั้ง","Login again",JOptionPane.ERROR_MESSAGE);
                       te.setVisible(true); 
                    break;  
                        
                        
                }
                server.close();  //ปิดการเชื่อมต่อ server 
                socketinput.close();//ปิด portinput
            }
            catch(Exception  ex){System.err.println(ex);}
        }
        catch (Exception exa)
        {
            JOptionPane.showMessageDialog(null,exa,"ERROR",JOptionPane.ERROR_MESSAGE);
          
        }
    }
     
public  void banweb()
  {
    new java.util.Timer().schedule(new TimerTask() 
    {
        @Override
        public void run() 
        {
            Connection connect;
            System.out.println("run = "+txtnameteacher);
            try 
            {
                Class.forName("com.mysql.jdbc.Driver"); //เชื่อมต่อ database โดยใช้ DriveManager
                connect =  DriverManager.getConnection("jdbc:mysql://"+IPServer+":3306/project" +"?user=bee&password=some_pass");
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery("select * from teacher where NameTeacher='"+txtnameteacher+"'");
                
                while(rs.next())
                {
                    
                    strbanweb = rs.getString("blockweb").trim();
//                    System.out.println(strbanweb);
                    String array[] = strbanweb.split("\n");
//                    File myFile = new File("C:\\Users\\TC710\\AppData\\Roaming\\Mozilla\\Firefox\\poo\\cache2\\entries"); //Path ของ Friefox
                    File myFile = new File("C:\\Users\\TC710\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\a3cp4es9.default\\sessionstore-backups");
                   for(int a=0;a<=array.length;a++)
                   {
                       
                        for (File s : myFile.listFiles()) 
                        {
                                            
                            String str = s.toString();
                            FileReader in = new FileReader(str); 
                            BufferedReader br = new BufferedReader(in);

//                        
                            for(String ss  ; ( ss = br.readLine() ) != null ; ) 			
                            {
                                
                                
                                if( ss.contains(array[a].trim()) )
                                {  
//                                       
                                    
                                    br.close();
                                    in.close();
                            
                                    socketoutput = new Socket(IPServer,30000);                              //set IP Address : PORT banweb 30000
                                    PrintWriter output = new PrintWriter(socketoutput.getOutputStream());   // สร้างเส้นทางข้อมูลไปยัง Server
                                    output.print("banweb\n");                                               //การส่งข้อมูลไปยังเครื่อง Server
                                    output.print(txtuser+"\n");
                                    output.print(txtcourse+"\n");
                                    output.print(txtnameteacher+"\n");
                                    
                                    
                                    System.out.println(txtuser + "\n" );
                                    System.out.println(txtcourse + "\n" );
                                    System.out.println(txtnameteacher + "\n" );
                                    
                                    
                                    
                                    
                                    
                                    
                                    output.flush();                                                             //Reset Object เพื่อรอรับข้อมูลจาก Server
                                    output.close(); // ปิด socketoutput 
                            
                                    for(File ff: myFile.listFiles()) 
                                    {
                                        ff.delete();  //ลบไฟล์ history ใน path Firefox
                                    }

                                    
                                    String cmds[] = new String[] {"cmd", "/c","Shutdown.bat"}; // ที่อยู่ เบทไฟล์ Shutdown 
                                    Runtime.getRuntime().exec(cmds); // เรียกการทำงาน Shutdown
                                    return;
                                }
                            }
                        
                
                        br.close();
                        in.close();
                    }  
                }
            }
            connect.close();


            } 

            catch (Exception e) 
            {
//                 JOptionPane.showMessageDialog(null,"ERROR","ERROR22222",JOptionPane.PLAIN_MESSAGE);
            }

          }
    }, 6000, 6000);
  
  }
    @SuppressWarnings("unchecked")
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("student Login");
        setBackground(new java.awt.Color(255, 51, 51));
        setUndecorated(true);
        getContentPane().setLayout(null);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(550, 310, 260, 30);

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox1PopupMenuWillBecomeVisible(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(550, 410, 260, 30);

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(jComboBox2);
        jComboBox2.setBounds(550, 470, 260, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("User");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(430, 320, 40, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Pass");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(430, 370, 40, 22);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Course");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(430, 420, 60, 22);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Teacher");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(430, 480, 80, 22);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(610, 530, 110, 50);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setText("Student Login");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(570, 240, 220, 50);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lab/UTK-LOGO.png"))); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(420, 60, 440, 140);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel7.setText("สาขาเทคโนโลยีคอมพิวเตอร์");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(830, 690, 420, 50);

        jTextField2.setText("jPasswordField1");
        getContentPane().add(jTextField2);
        jTextField2.setBounds(550, 360, 260, 30);

        setSize(new java.awt.Dimension(1259, 738));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
      
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtnameteacher = jComboBox2.getSelectedItem().toString();
        System.out.println(txtnameteacher);
        btOK();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBox1PopupMenuWillBecomeVisible
      
    }//GEN-LAST:event_jComboBox1PopupMenuWillBecomeVisible

    public static studentLogin te;
    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                te = new studentLogin();
                te.setVisible(true); //เปิดการหน้า studentLogin
                te.setAlwaysOnTop(true);
            }
        });
    }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPasswordField jTextField2;
    // End of variables declaration//GEN-END:variables
}
