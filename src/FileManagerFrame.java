
import javax.swing.JInternalFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eric
 */
public class FileManagerFrame extends JInternalFrame{
    //Still need to look at split panes
    public FileManagerFrame() {
        this.setTitle("C:\\"); //get current file path later
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(250,200);
        this.setVisible(true);
    }
}
