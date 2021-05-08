
import java.awt.Dimension;
import java.io.File;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

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
    boolean isClosed;
    
    public FileManagerFrame(File file) {
        dirPanel = new DirPanel(file);
        filePanel = new FilePanel(file);
        dirPanel.setFilePanel(filePanel);
        this.setTitle(file.toString()); 
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
        isClosed = false;
        
        setMinimumSize();
        
        this.getContentPane().add(splitPane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setBounds(0, 100, 700, 500);
        this.setResizable(true);
        this.setVisible(true);
        
        addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
                isClosed = true;
            }
        });
    }
    
    private void setMinimumSize(){
        Dimension minimumSize = new Dimension(200, 200);
        dirPanel.setMinimumSize(minimumSize);
        filePanel.setMinimumSize(minimumSize);
    }
}
