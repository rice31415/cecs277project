
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
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
    
    public App() {
        mainPanel = new JPanel();
        topPanel = new JPanel();
        button = new JButton("Button");
       
        
    }
    public void go() {
        this.setTitle("CECS 277 File Manager");
        mainPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.setBackground(Color.WHITE);
        topPanel.add(button);
        button.addActionListener(new okActionListener());
        
        this.add(mainPanel);
        this.setSize(690, 420);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
