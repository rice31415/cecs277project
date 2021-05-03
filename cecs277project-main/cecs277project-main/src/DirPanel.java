
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eric, Kevin
 */
public class DirPanel extends JPanel{
    private JScrollPane scrollPane = new JScrollPane();
    private JTree dirTree = new JTree();
    private DefaultTreeModel treeModel;
    private FilePanel filePanel;
    
    public DirPanel(File file) {
        scrollPane.setViewportView(dirTree);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        buildTree(file);
        scrollPane.setSize(new Dimension(400, 4000));
        dirTree.setSize(scrollPane.getSize());
        //To dynamically edit the size, I think we're supposed to run ^ line whenever mouse released
        //I'll wait to see if Hoffman will go over mouse listeners
        dirTree.addTreeSelectionListener(new treeSelectionListener());
    }
    
    //TODO: Some file icons not updating when expanding branches
    private void buildTree(File rootFile){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootFile.toString());
        treeModel = new DefaultTreeModel(root);
        DefaultMutableTreeNode subnode = null;
        File[] dirs = rootFile.listFiles();
        for (int i = 0; i < dirs.length; i++){
            if (dirs[i].isDirectory()){
                subnode = new DefaultMutableTreeNode(dirs[i].getName());
                root.add(subnode);
                expandBranch(dirs[i], subnode);
            }
        }
        dirTree.setModel(treeModel);
    }
    
    private void expandBranch(File file, DefaultMutableTreeNode node){
        File[] subFiles = file.listFiles();
        DefaultMutableTreeNode subnode = null;
        if (subFiles != null){
            for (int i = 0; i < subFiles.length; i++){
                FileNode fileNode = new FileNode(subFiles[i].toString());
                subnode = new DefaultMutableTreeNode(fileNode);
                node.add(subnode);
            }
        }
    }
    
    public void setFilePanel(FilePanel fp) {
        filePanel = fp;
    }

    class treeSelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
                    dirTree.getLastSelectedPathComponent();
            FileNode fNode = (FileNode) node.getUserObject();
            //System.out.println(fNode.toString());
            expandBranch(fNode.getFile(), node);
            
            Desktop desktop = Desktop.getDesktop();
            try{
                if (!fNode.getFile().isDirectory()){
                    desktop.open(fNode.getFile());
                }
                else filePanel.fillList(new File(fNode.getFile().getName()));
            }
            catch (IOException ex){
                
            }
        }
    }
}
