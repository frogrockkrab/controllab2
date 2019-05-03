/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author admin
 */
public class BlockProgram {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh mm ss a");
    String Viewer;
    String exec_command = "tasklist.exe /FO LIST";
    Timer timer;

    public BlockProgram() {
        timer = new Timer();
        timer.schedule(blockprogram,0, 5*1000);
    }
    
    private ArrayList<String> GetProcessListData() {
        ArrayList<String> data = new ArrayList<String>();

        try {
            Process p = Runtime.getRuntime().exec(exec_command);
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            while (str != null) {
                if (str.startsWith("Image Name:")) {
                    String appName = str.substring(11).trim();
                    data.add(appName);
                }
                str = in.readLine();
            }
        } catch (IOException e) {
            System.out.println("Exception arise during the read Processes");
            e.printStackTrace();
        }
        return data;
    }

    private ArrayList<String> GetBanList() {
        ArrayList<String> ban = new ArrayList<String>();

        ban.add("Garena");
        ban.add("Discord");
        ban.add("Steam");

        return ban;

    }

    public void cap() throws Exception {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", new File("d:\\" + formatter.format(now.getTime()) + ".jpg"));

    }

    public static void main(String[] args){
        BlockProgram C = new BlockProgram();
        
    }

    TimerTask blockprogram = new TimerTask() {
        public void run() {
            
            ArrayList<String> Data = GetProcessListData();
            ArrayList<String> Ban = GetBanList();
            ArrayList<String> all_bannedlist = new ArrayList<>();

            for (int i = 0; i < Data.size(); i++) {
                System.out.println(Data.get(i));
            }
            for (int i = 0; i < Ban.size(); i++) {
                System.out.println(Ban.get(i));
            }

            for (int i = 0; i < Data.size(); i++) {
                for (int j = 0; j < Ban.size(); j++) {
                    if (Data.get(i).matches(Ban.get(j) + ".*")) {
                        System.out.println(Data.get(i) + " is matched " + Ban.get(j));
                        all_bannedlist.add(Data.get(i));
                        if (Data.get(i).matches(Ban.get(j) + ".*") == true) {
                            try {
                                cap();
                            } catch (Exception ex) {
                                Logger.getLogger(BlockProgram.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    }
                }
            }
        }
    };

}
