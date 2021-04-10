
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eric
 */
class App extends JFrame {
    
    JPanel mainPanel, topPanel;
    JButton button;
    JMenuBar menuBar;
    
    public App() {
        mainPanel = new JPanel();
        topPanel = new JPanel();
        button = new JButton("Button");
        menuBar = new JMenuBar();
    }
    
    public void go() {
        this.setTitle("CECS 277 File Manager");
        mainPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        
        buildMenu();
        
        topPanel.add(menuBar, BorderLayout.NORTH);
        topPanel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new okActionListener());
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        this.add(mainPanel);
        this.setSize(690, 420);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void buildMenu() {
        buildFileMenu();
        buildTreeMenu();
        buildWinMenu();
        buildHelpMenu();
    }
    
    private void buildFileMenu() {
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem rename = new JMenuItem("Rename");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem run = new JMenuItem("Run"); 
        JMenuItem exit = new JMenuItem("Exit");
        
        exit.addActionListener(new ExitActionListener());
        
        fileMenu.add(rename);
        fileMenu.add(copy);
        fileMenu.add(delete);
        fileMenu.add(run);
        fileMenu.add(exit);
        
        menuBar.add(fileMenu);
    }
    
    private void buildTreeMenu() {
        JMenu treeMenu = new JMenu("Tree");
        
        JMenuItem expand = new JMenuItem("Expand Branch");
        JMenuItem col = new JMenuItem("Collapse Branch");
        
        treeMenu.add(expand);
        treeMenu.add(col);
        
        menuBar.add(treeMenu);
    }
    
    private void buildWinMenu() {
        JMenu winMenu = new JMenu("Window");
        
        JMenuItem newWin = new JMenuItem("New");
        JMenuItem cascade = new JMenuItem("Cascade");
        
        winMenu.add(newWin);
        winMenu.add(cascade);
        
        menuBar.add(winMenu);
    }
    
    private void buildHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");
        
        helpMenu.add(helpItem);
        helpMenu.add(about);
        
        about.addActionListener(new AboutActionListener());
        
        menuBar.add(helpMenu);
        
    }
    
    private class AboutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AboutDialog dialog = new AboutDialog(null, false);
            dialog.setVisible(true);
        }

        
    }

    private class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

        
    }

    private class okActionListener implements ActionListener {

        public okActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("OK")){
                System.out.println("OK Button Pressed");
            }
            else System.out.println("Cancel Button Pressed");
        }
    }
}

