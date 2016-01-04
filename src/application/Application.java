package application;

import control.Command;
import control.NextImageCommand;
import control.PrevImageCommand;
import model.Image;
import view.ImageDisplay;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application extends JFrame{

    public static void main(String[] args) {
        new Application().setVisible(true);
    }
    
    private final Map<String, Command> commands = new HashMap<>();
    private ImageDisplay imageDisplay;
    
    public Application() {
        this.deployUI();
        this.createCommands();
    }

    private void deployUI() {
        this.setTitle("ImageViewer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(imagePanel());
        this.getContentPane().add(toolbar(), BorderLayout.SOUTH);
    }

    private void createCommands() {
        commands.put("next", new NextImageCommand(imageDisplay));
        commands.put("prev", new PrevImageCommand(imageDisplay));
    }

    private ImagePanel imagePanel() {
        ImagePanel imagePanel = new ImagePanel(getImage());
        imageDisplay = imagePanel;
        return imagePanel; 
    }

    private Image getImage() {
        return new FileImageReader("C:\\Users\\Chaxi\\IMAGEVIEWER").read();
    }

    private Component toolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        panel.add(prevButton());
        panel.add(nextButton()); 
        return panel;
    }
    
    private JButton nextButton() {
        JButton nextButton = new JButton(">>");
        nextButton.addActionListener(doCommand("next"));
        nextButton.addKeyListener(pressKey());
        return nextButton;
    }

    private JButton prevButton() {
        JButton prevButton = new JButton("<<");
        prevButton.addActionListener(doCommand("prev"));
        prevButton.addKeyListener(pressKey());
        return prevButton;
    }

    private ActionListener doCommand(final String operation) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(operation).execute();
            }
        };
    }

    private KeyListener pressKey() {
        return new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int b = e.getKeyCode();
                
                if ( b == KeyEvent.VK_LEFT){
                    commands.get("prev").execute();
                }
                
                if (b == KeyEvent.VK_RIGHT){
                    commands.get("next").execute();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };
    }

    

    

}
