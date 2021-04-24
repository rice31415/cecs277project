
import java.awt.Dimension;
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
    
    public FileManagerFrame() {
        dirPanel = new DirPanel();
        filePanel = new FilePanel();
        
        this.setTitle("C:\\"); //get current file path later
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
        
        setMinimumSize();
        
        this.getContentPane().add(splitPane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(900, 900);
        this.setVisible(true);
    }
    
    private void setMinimumSize(){
        Dimension minimumSize = new Dimension(200, 200);
        dirPanel.setMinimumSize(minimumSize);
        filePanel.setMinimumSize(minimumSize);
    }
}
