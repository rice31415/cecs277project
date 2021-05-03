
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
    private JPopupMenu popup = new JPopupMenu();
    private File currentFile;
    
    public FilePanel(File file) {
        buildModel(file);
        buildPopup();
        currentFile = file;
        
        scrollPane.setViewportView(fileList);
        
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setSize(new Dimension(400, 4000));
        fileList.setSize(scrollPane.getSize());
        fileList.addListSelectionListener(new fileSelectionListener());
        
        this.add(scrollPane);
        this.setDropTarget(new MyDropTarget());
        
        
    }
    
    private void buildModel(File file) {
        File[] files = file.listFiles();
        if (files != null){
            for (int i = 0; i < files.length; i++){
                FileNode fileNode = new FileNode(files[i].toString(), files[i]);
                model.addElement(fileNode);
            }
        }
        fileList.setDragEnabled(true);
        fileList.setModel(model);
    }
    
    private void buildPopup() {
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem rename = new JMenuItem("Rename");
        JMenuItem delete = new JMenuItem("Delete");
        
        copy.addActionListener(new MainActionListener());
        rename.addActionListener(new MainActionListener());
        delete.addActionListener(new MainActionListener());
        
        popup.add(copy);
        popup.add(rename);
        popup.add(delete);
        
        MouseListener popupListener = new PopupListener();
        fileList.addMouseListener(popupListener);
    }
    
    public void fillList(File dir) {
        File[] files;
        files = dir.listFiles();
        model.clear();
        fileList.removeAll();
        for(int i = 0; i < files.length; i++) {
            FileNode fileNode = new FileNode(files[i].toString(), files[i]);
            model.addElement(fileNode);
        }
        fileList.setModel(model);
    }
    
    public void setDetail(boolean d) {
        for (int i = 0; i < model.getSize(); i++) {
            FileNode fileNode = (FileNode)model.getElementAt(i);
            fileNode.setDetail(d);
        }
        scrollPane.repaint();
    }
    
    public void setCurrentFile(File file){
        currentFile = file;
    }
    
    public File getCurrentFile(){
        return currentFile;
    }
    
    class MyDropTarget extends DropTarget {
        public void drop(DropTargetDropEvent event) {
            try {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                List result = new ArrayList();
                if(event.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)){     
                    String temp = (String)event.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    String[] next = temp.split("\\n");
                    for(int i=0; i<next.length;i++) {
                        model.addElement(next[i]); 
                    }
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
    
    class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
        private void getLastPopupMouseEvent(MouseEvent e){
            System.out.println("Testing");
        }
    }
    
    private class MainActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Copy")){
                CopyDialog copyDlg = new CopyDialog(null, true);
                copyDlg.setDirectoryLabel(currentFile.toString());
                copyDlg.setFromField(currentFile.toString());
                copyDlg.setVisible(true);
                if (!copyDlg.canceled){
                    String toField = copyDlg.getToField();
                    System.out.println("toField: " + toField);
                }
            }
            else if (e.getActionCommand().equals("Rename")){
                RenameDialog renameDlg = new RenameDialog(null, true);
                renameDlg.setDirectoryLabel(currentFile.toString());
                renameDlg.setFromField(currentFile.toString());
                renameDlg.setVisible(true);
                if (!renameDlg.canceled){
                    String toField = renameDlg.getToField();
                    System.out.println("toField: " + toField);
                }
            }
            else if (e.getActionCommand().equals("Delete")){
                DeleteDialog deleteDlg = new DeleteDialog(null, true);
                deleteDlg.setDeleteLabel(currentFile.toString());
                deleteDlg.setVisible(true);
                if (!deleteDlg.canceled){
                    System.out.println("Deleting file");
                }
            }
        }
    }
    private class fileSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent evt) {
            if (!evt.getValueIsAdjusting()) {
                FileNode fileNode = (FileNode)fileList.getSelectedValue();
                File file = fileNode.getFile();
                Desktop desktop = Desktop.getDesktop();
                try{
                    if (!file.isDirectory()){
                        currentFile = file;
                        desktop.open(file);
                    }
                }
                catch (IOException ex){
                    
                }
            }
            
        }
    }
}
