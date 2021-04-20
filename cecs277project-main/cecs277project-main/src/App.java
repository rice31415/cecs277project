
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    JDesktopPane desktopPane;
    JMenuBar menuBar, statusBar;
    
    public App() {
        mainPanel = new JPanel();
        topPanel = new JPanel();
        button = new JButton("Button");
        desktopPane = new JDesktopPane();
        menuBar = new JMenuBar();
        statusBar = new JMenuBar();
        
        mainPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
    }
    
    public void go() {
        this.setTitle("CECS 277 File Manager");
        
        buildMenu();
        buildStatusBar();
        
        topPanel.add(menuBar, BorderLayout.NORTH);
        
        FileManagerFrame fileManFrame = new FileManagerFrame();
        desktopPane.add(fileManFrame);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(desktopPane, BorderLayout.CENTER);
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        this.add(mainPanel);
        this.setSize(750, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    //Creates the top menu bar based on Hoffman's directions
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
        
        exit.addActionListener(new MainActionListener());
        
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
        
        newWin.addActionListener(new MainActionListener());
        
        menuBar.add(winMenu);
    }
    
    private void buildHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");
        
        helpMenu.add(helpItem);
        helpMenu.add(about);
        
        about.addActionListener(new MainActionListener());
        
        menuBar.add(helpMenu);
        
    }
    
    private void buildStatusBar() {
        JLabel status = new JLabel(displayDiskStatus("C:\\")); //replace string with something to read current drive later
        statusBar.add(status);
    }
    
    //Info for Status Bar, need to convert space into MB/GB
    private String displayDiskStatus(String diskPath) {
        File file = new File(diskPath);
        String status = "Current Drive: " + diskPath +
                        " Free Space: " + file.getFreeSpace() + 
                        " Used Space: " + (file.getTotalSpace()-file.getFreeSpace()) +
                        " Total Space: " + file.getTotalSpace();
        return status;
    }
    
    //Probably going to return something to File Panel later
    private void displayDisk(String diskPath) {
        File file = new File(diskPath);
        File[] files = file.listFiles();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat dFormatter = new DecimalFormat("#,###");
        
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                System.out.println("Directory: " + files[i].getAbsolutePath() + 
                                   " Date: " + formatter.format(files[i].lastModified()) +
                                   " Size: " + dFormatter.format(files[i].length()));
            }
            else System.out.println("File: " + files[i].getAbsolutePath() + 
                                    " Date: " + formatter.format(files[i].lastModified()) +
                                    " Size: " + dFormatter.format(files[i].length()));
        }
    }
    
    private void openFile(String filePath) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(filePath));
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    
    //Class used for all ActionListeners
    private class MainActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("About")){
                AboutDialog dialog = new AboutDialog(null, false);
                dialog.setVisible(true);
            }
            else if (e.getActionCommand().equals("Exit")){
                System.exit(0);
            }
            else if (e.getActionCommand().equals("OK")){
                System.out.println("OK Button Pressed");
            }
            else if (e.getActionCommand().equals("New")){
                desktopPane.add(new FileManagerFrame());
            }
            else {
                System.out.println("Cancel Button Pressed");
            }
        }
    }
}

