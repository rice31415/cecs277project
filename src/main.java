
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eric
 */
public class main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App app = new App();
        app.go();
    }
    
    //Displays all the files in a disk along with path, date modified, and size
    //Will need to put info into a panel of some sort later but not sure how and where at the moment
    public void displayDisk(String diskPath) {
        File file = new File(diskPath);
        File[] files = file.listFiles();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat dFormatter = new DecimalFormat("#,###");
        
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                System.out.println("Directory: " + files[i].getAbsolutePath() + 
                                   " Date: " + formatter.format(files[i].lastModified()) +
                                   " Size: " + dFormatter.format(files[i].length()));
            }
            else System.out.println("File: " + files[i].getAbsolutePath() + 
                                    " Date: " + formatter.format(files[i].lastModified()) +
                                    " Size: " + dFormatter.format(files[i].length()));
        }
    }
    
    //Info for Status Bar later
    public void displayDiskStatus(String diskPath) {
        File file = new File(diskPath);
        System.out.println("Current Drive: " + diskPath +
                           " Free Space: " + file.getFreeSpace() + 
                           " Used Space: " + (file.getTotalSpace()-file.getFreeSpace()) +
                           " Total Space: " + file.getTotalSpace());
    }
    
}
