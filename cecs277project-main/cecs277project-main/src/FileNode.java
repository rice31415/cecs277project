
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
 * @author Eric, Kevin
 */
class FileNode {
    File file;
    String fileName;
    boolean detail;
    
    public FileNode(String fileName){
        file = new File(fileName);
        detail = false;
    }
    
    public FileNode(String name, File file){
        fileName = name;
        this.file = file;
        detail = false;
    }
    
    public File getFile(){
        return file;
    }
    
    public String toString(){
        if (file.getName().equals("")){
            return file.getPath();
        }
        if (detail) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            DecimalFormat dFormatter = new DecimalFormat("#,###");
            //file.getName()
            String str = String.format("%-40s%40s%40s", file.getName(), 
                        formatter.format(file.lastModified()),
                        dFormatter.format(file.length()));
            /*return file.getAbsolutePath() + 
                    formatter.format(file.lastModified()) +
                    dFormatter.format(file.length());*/
            return str;
        }
        else return file.getName();
    }
    
    public void setDetail(boolean d) {
        detail = d;
    }
    
    public boolean isDirectory(){
        return file.isDirectory();
    }
}
