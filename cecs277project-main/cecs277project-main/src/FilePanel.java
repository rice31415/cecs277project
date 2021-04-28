
import java.awt.BorderLayout;
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
    
    public FilePanel(File file) {
        buildModel(file);
        
        scrollPane.setViewportView(fileList);
        
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setSize(new Dimension(400, 4000));
        fileList.setSize(scrollPane.getSize());
        
        this.add(scrollPane);
        this.setDropTarget(new MyDropTarget());
        
        
    }
    
    private void buildModel(File file) {
        File[] files = file.listFiles();
        if (files != null){
            for (int i = 0; i < files.length; i++){
                FileNode fileNode = new FileNode(files[i].toString());
                model.addElement(fileNode);
            }
        }
        fileList.setDragEnabled(true);
        fileList.setModel(model);
    }

    class MyDropTarget extends DropTarget {
        public void drop(DropTargetDropEvent event) {
            try {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                List result = new ArrayList();
                if(event.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)){     
                    String temp = (String)event.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    String[] next = temp.split("\\n");
                    for(int i=0; i<next.length;i++)
                        model.addElement(next[i]); 
                }
                else{ 
                    result =(List)event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    //process input
                    for (Object o: result) {
                        System.out.println(o.toString());
                        model.addElement(o.toString());
                        //Files.copy((File)o, get current directory path;
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
