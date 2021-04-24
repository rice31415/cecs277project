
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eric, Kevin
 */
public class FilePanel extends JPanel {
    private JScrollPane scrollPane = new JScrollPane();
    private JList fileList = new JList();
    DefaultListModel model = new DefaultListModel();
    
    public FilePanel() {
        buildModel();
        
        scrollPane.setViewportView(fileList);
        scrollPane.setPreferredSize(new Dimension(400, 4000));
        fileList.setPreferredSize(scrollPane.getSize());
        this.add(scrollPane);
        this.setDropTarget(new MyDropTarget());
        
    }
    
    private void buildModel() {
        model.addElement("First");
        model.addElement("Second");
        model.addElement("Third");
        fileList.setPreferredSize(new Dimension(400, 4000));
        fileList.setModel(model);
    }

    class MyDropTarget extends DropTarget {
        public void drop(DropTargetDropEvent event) {
            try {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                List result = new ArrayList();
                result = (List)event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                
                //process the input later(add files to whatever directory you're on)
                
                for (Object o: result) {
                    System.out.println(o.toString());
                    model.addElement(o.toString());
                    //Files.copy((File)o, get current directory path;
                }
            }
            catch(Exception e) {
                
            }
        }
    }
}
