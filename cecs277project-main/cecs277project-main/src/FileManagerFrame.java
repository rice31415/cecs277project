
import java.awt.Dimension;
import java.io.File;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eric, Kevin
 */
public class FileManagerFrame extends JInternalFrame{
    JSplitPane splitPane;
    DirPanel dirPanel;
    FilePanel filePanel;
    
    public FileManagerFrame(File file) {
        dirPanel = new DirPanel(file);
        filePanel = new FilePanel(file); //might have to take in different file parameter
        this.setTitle(file.toString()); 
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
        
        setMinimumSize();
        
        this.getContentPane().add(splitPane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(500, 500);
        this.setResizable(true);
        this.setVisible(true);
    }
    
    private void setMinimumSize(){
        Dimension minimumSize = new Dimension(200, 200);
        dirPanel.setMinimumSize(minimumSize);
        filePanel.setMinimumSize(minimumSize);
    }
}
