
package Test;

 
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
 
/**
 *
 * @author Muneeswaran
 */
public class DetectProgram
{
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh mm ss a");
    String Viewer;
    String exec_command ="tasklist.exe /FO LIST";
    private ArrayList<String> GetProcessListData()
    {
       ArrayList<String> data = new ArrayList<String>();
        
      try {
            Process p = Runtime.getRuntime().exec(exec_command);
            BufferedReader in
               = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            while (str!=null) {
                if (str.startsWith("Image Name:")) {
                    String appName = str.substring(11).trim();
                    data.add(appName);
                }
                str = in.readLine();
            }
        }catch (IOException e) {
               System.out.println("Exception arise during the read Processes");
               e.printStackTrace();
               } 
                return data;
     }
    private ArrayList<String>GetBanList(){
        ArrayList<String> ban = new ArrayList<String>();

            ban.add("Garena");
            ban.add("Discord");
            ban.add("Steam");
            
            return ban;
            
}
    public void cap() throws Exception
    {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", new File("E:\\"+formatter.format(now.getTime())+".jpg"));

    }

 public static void main(String[] args) throws Exception
 {
      DetectProgram C = new DetectProgram();
      DetectProgram T = new DetectProgram();
      DetectProgram B = new DetectProgram();
 String path = new File("E://shutdown.bat").getPath();
 T.GetProcessListData();
 B.GetBanList();
 ArrayList<String> Data=T.GetProcessListData();
 ArrayList<String> Ban=B.GetBanList();
 ArrayList<String> all_bannedlist = new ArrayList<String>();
 for(int i =0;i<Data.size();i++){
    System.out.println(Data.get(i));
 }
  for(int i =0;i<Ban.size();i++){
    System.out.println(Ban.get(i));
 }
  
       for(int i=0;i<Data.size();i++){
           for(int j=0;j<Ban.size();j++){
               if(Data.get(i).matches(Ban.get(j)+".*")){
                   System.out.println(Data.get(i)+" is matched "+Ban.get(j));
                    all_bannedlist.add(Data.get(i));
                    if(Data.get(i).matches(Ban.get(j)+".*")==true){
                        C.cap();
                        Runtime.getRuntime().exec(path);}
                        break;
                    }



                
               /*if(Data.get(i).equals(Ban.get(j))){
                    all_bannedlist.add(Data.get(i));
                    Runtime.getRuntime().exec(path);
                    
               }*/
                   
        } 
        }
 }
}