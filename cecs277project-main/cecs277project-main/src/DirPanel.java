
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
    
    //Using C drive
    //TODO: Some file icons not updating when expanding branches
    private void buildTree(File file){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.toString());
        treeModel = new DefaultTreeModel(root);
        File[] files = file.listFiles();
        if (files != null){
            for (int i = 0; i < files.length; i++){
                FileNode fileNode = new FileNode(files[i].toString());
                DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(fileNode);
                root.add(subnode);
                expandBranch(files[i], subnode);
            }
        }
        dirTree.setModel(treeModel);
    }
    private void expandBranch(File f, DefaultMutableTreeNode n){
        if (f.listFiles() != null){
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++){
                FileNode fileNode = new FileNode(files[i].toString());
                DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(fileNode);
                
                //Prevents duplicate subnodes from being added
                if (n.getChildCount() != files.length){
                    n.add(subnode);
                    expandSubBranch(files[i], subnode);
                }
                
            }
        }
    }
    private void expandSubBranch(File f, DefaultMutableTreeNode n){
        if (f.listFiles() != null){
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++){
                FileNode fileNode = new FileNode(files[i].toString());
                DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(fileNode);
                n.add(subnode);
            }
        }
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
            }
            catch (IOException ex){
                
            }
        }
    }
}
