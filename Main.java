import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main{
  
  public static void main(String[] args) {
        World.initWorld();

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(500, 500);

        Screen screen = new Screen();
        frame.add(screen, BorderLayout.CENTER);
        frame.repaint();

        screen.setFocusable(true);
        screen.requestFocusInWindow();
        screen.addKeyListener(screen);

        World.screen = screen;

        World.Start();
    }
}