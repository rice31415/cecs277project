
import java.io.File;
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
 * @author Eric
 */
public class DirPanel extends JPanel{
    private JScrollPane scrollPane = new JScrollPane();
    private JTree dirTree = new JTree();
    private DefaultTreeModel treeModel;
    
    public DirPanel() {
        scrollPane.setViewportView(dirTree);
        this.add(scrollPane);
        buildTree();
        dirTree.addTreeSelectionListener(new treeSelectionListener());
    }
    
    //Using C drive; will fix later
    private void buildTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(root);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Node");
        root.add(node);
        File[] paths = File.listRoots();
        File[] files = paths[0].listFiles();
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory()){
                FileNode fileNode = new FileNode("File: " + files[i]);
                DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(fileNode);
                node.add(subnode);
            }
        }
        
        dirTree.setModel(treeModel);
    }

    class treeSelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
                    dirTree.getLastSelectedPathComponent();
            FileNode fNode = (FileNode) node.getUserObject();
            System.out.println(fNode.toString());
        }
    }
}
