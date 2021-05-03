
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * @author Eric, Kevin
 */
class App extends JFrame {
    
    JPanel mainPanel, topPanel;
    JButton dButton, sButton;
    JDesktopPane desktopPane;
    JMenuBar menuBar, statusBar;
    JToolBar toolBar;
    JComboBox drives;
    File currentDrive;
    
    public App() {
        mainPanel = new JPanel();
        topPanel = new JPanel();
        dButton = new JButton("Details");
        sButton = new JButton("Simple");
        desktopPane = new JDesktopPane();
        menuBar = new JMenuBar();
        statusBar = new JMenuBar();
        toolBar = new JToolBar();
        currentDrive = new File("C:\\");
        
        mainPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
    }
    
    public void go() {
        this.setTitle("CECS 277 File Manager");
        
        buildMenu();
        buildStatusBar();
        buildToolBar();
        
        topPanel.add(menuBar, BorderLayout.NORTH);
        topPanel.add(toolBar, BorderLayout.SOUTH);
        
        FileManagerFrame fileManFrame = new FileManagerFrame(new File("C:\\"));
        desktopPane.add(fileManFrame);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(desktopPane, BorderLayout.CENTER);
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        this.add(mainPanel);
        this.setSize(1000, 1000);
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
        
        rename.addActionListener(new MainActionListener());
        copy.addActionListener(new MainActionListener());
        delete.addActionListener(new MainActionListener());
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
        JLabel status = new JLabel(displayDiskStatus(currentDrive));
        statusBar.add(status);
    }
    
    private void buildToolBar() {
        File[] paths;
        paths = File.listRoots();
        drives = new JComboBox(paths);
        drives.setMaximumSize(new Dimension(100, 50));
        drives.addActionListener(new MainActionListener());
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(drives);
        toolBar.add(dButton);
        toolBar.add(sButton);
        toolBar.setMargin(new Insets(5, 5, 5, 5));
        toolBar.setFloatable(false);
        dButton.addActionListener(new MainActionListener());
        sButton.addActionListener(new MainActionListener());
        
    }
    
    //Info for Status Bar, need to convert space into MB/GB
    private String displayDiskStatus(File diskPath) {
        String status = "Current Drive: " + diskPath +
                        "    Free Space: " + (int)(diskPath.getFreeSpace()/(Math.pow(10,9))) + 
                        "GB    Used Space: " + (int)((diskPath.getTotalSpace()-diskPath.getFreeSpace())/(Math.pow(10,9))) +
                        "GB    Total Space: " + (int)(diskPath.getTotalSpace()/(Math.pow(10, 9))) + "GB";
        return status;
    }
    
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
                desktopPane.add(new FileManagerFrame(currentDrive));
            }
            //When a new drive is selected in drive list:
            else if (e.getActionCommand().equals("comboBoxChanged")){
                currentDrive = (File)drives.getSelectedItem();
                statusBar.removeAll();
                statusBar.add(new JLabel(displayDiskStatus(currentDrive)));
            }
            else if (e.getActionCommand().equals("Copy")){
                FileManagerFrame active = (FileManagerFrame)desktopPane.getSelectedFrame();
                if (active == null) {
                    return;
                }
                FilePanel fp = active.filePanel;
                
                CopyDialog copyDlg = new CopyDialog(null, true);
                copyDlg.setDirectoryLabel(fp.getCurrentFile().toString());
                copyDlg.setFromField(fp.getCurrentFile().toString());
                copyDlg.setVisible(true);
                if (!copyDlg.canceled){
                    String toField = copyDlg.getToField();
                    System.out.println("toField: " + toField);
                }
            }
            else if (e.getActionCommand().equals("Rename")){
                FileManagerFrame active = (FileManagerFrame)desktopPane.getSelectedFrame();
                if (active == null) {
                    return;
                }
                FilePanel fp = active.filePanel;
                
                RenameDialog renameDlg = new RenameDialog(null, true);
                renameDlg.setDirectoryLabel(fp.getCurrentFile().toString());
                renameDlg.setFromField(fp.getCurrentFile().toString());
                renameDlg.setVisible(true);
                if (!renameDlg.canceled){
                    String toField = renameDlg.getToField();
                    System.out.println("toField: " + toField);
                }
            }
            else if (e.getActionCommand().equals("Delete")){
                FileManagerFrame active = (FileManagerFrame)desktopPane.getSelectedFrame();
                if (active == null) {
                    return;
                }
                FilePanel fp = active.filePanel;
                
                DeleteDialog deleteDlg = new DeleteDialog(null, true);
                deleteDlg.setDeleteLabel(fp.getCurrentFile().toString());
                deleteDlg.setVisible(true);
                if (!deleteDlg.canceled){
                    System.out.println("Deleting file");
                }
            }
            else if(e.getActionCommand().equals("Details")) {
                FileManagerFrame active = (FileManagerFrame)desktopPane.getSelectedFrame();
                if (active == null) {
                    return;
                }
                FilePanel fp = active.filePanel;
                fp.setDetail(true);
            }
            else if(e.getActionCommand().equals("Simple")) {
                FileManagerFrame active = (FileManagerFrame)desktopPane.getSelectedFrame();
                if (active == null) {
                    return;
                }
                FilePanel fp = active.filePanel;
                fp.setDetail(false);
            }
            else {
                System.out.println("Cancel Button Pressed");
            }
        }
    }
    
}

