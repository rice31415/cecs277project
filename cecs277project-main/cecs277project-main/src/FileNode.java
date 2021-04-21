
import java.io.File;

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
    
    public FileNode(String fileName){
        file = new File(fileName);
    }
    
    public FileNode(String name, File file){
        fileName = name;
        this.file = file;
    }
    
    public File getFile(){
        return file;
    }
    
    public String toString(){
        if (file.getName().equals("")){
            return file.getPath();
        }
        return file.getName();
    }
    
    public boolean isDirectory(){
        return file.isDirectory();
    }
}
