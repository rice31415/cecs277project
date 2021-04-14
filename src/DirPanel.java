
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

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
    
    public DirPanel() {
        scrollPane.setViewportView(dirTree);
        this.add(scrollPane);
    }
}
