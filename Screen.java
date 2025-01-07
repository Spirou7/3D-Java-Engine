import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

public class Screen extends JPanel
    implements ActionListener, KeyListener, MouseMotionListener, MouseListener {

  // Holds the pixels that will painted to the screen on each frame
  // Can be edited by different classes in order to change the frame
  public Color[][] pixels = new Color[500][500];

  // Create a new viewer object
  public Screen() {
    super();
    this.addMouseMotionListener(this);
    this.addMouseListener(this);

    initSnowFlakes(100);

    // this.setFocusable(true);
    // this.requestFocusInWindow();
    // this.addKeyListener(this);
  }

  public void actionPerformed(ActionEvent e) {

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    pixels = new Color[getWidth()][getHeight()];


    setScreenWhite();
    World.updatePixels(this);

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        if (!pixels[i][j].equals(Color.WHITE)) {
          g.setColor(pixels[i][j]);
          g.fillRect(i - 5, j - 5, 10, 10);
        }
      }
    }

    // Add snowflakes to the screen
    updateSnowFlakes();
    for(int i = 0; i < snowFlakes.size(); i++){
      Vector3 point = snowFlakes.get(i);
      g.setColor(Color.GRAY);
      g.fillOval((int)(point.x - 5), (int)point.y - 5, 10, 10);
    }
  }

  ArrayList<Vector3> snowFlakes = new ArrayList<Vector3>();
  ArrayList<Vector3> snowFlakeVelocity = new ArrayList<Vector3>();

  public void initSnowFlakes(int count){
    snowFlakes = new ArrayList<Vector3>();
    snowFlakeVelocity = new ArrayList<Vector3>();

    for(int i = 0; i < count; i++){
      int x = (int)(Math.random() * getWidth());
      snowFlakes.add(new Vector3(x, 0, 0));
      snowFlakeVelocity.add(new Vector3(Math.random() * 10 - 5, Math.random() * 5, 0));
    }
  }

  public void updateSnowFlakes(){
    for(int i = 0; i < snowFlakes.size(); i++){
      Vector3 velocity = snowFlakeVelocity.get(i);
      Vector3 snow = snowFlakes.get(i);
      snow.x += velocity.x;
      snow.y += velocity.y;

      if(snow.x >= getWidth() || snow.y >= getHeight()){
        snow.x = Math.random() * getWidth();
        snow.y = 0;
      }
    }
  }

  public void setScreenWhite() {
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = Color.WHITE;
      }
    }
  }

  public static HashSet<Integer> buttonsDown = new HashSet<Integer>();

  public void keyPressed(KeyEvent e) {
    buttonsDown.add(e.getKeyCode());
  }

  public void keyReleased(KeyEvent e) {
    buttonsDown.remove(new Integer(e.getKeyCode()));
  }

  private void moveMouse(Point p) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gs = ge.getScreenDevices();

    // Search the devices for the one that draws the specified point.
    for (GraphicsDevice device : gs) {
      GraphicsConfiguration[] configurations = device.getConfigurations();
      for (GraphicsConfiguration config : configurations) {
        Rectangle bounds = config.getBounds();
        if (bounds.contains(p)) {
          // Set point to screen coordinates.
          Point b = bounds.getLocation();
          Point s = new Point(p.x - b.x, p.y - b.y);

          try {
            Robot r = new Robot(device);
            r.mouseMove(s.x, s.y);
          } catch (AWTException e) {
            e.printStackTrace();
          }

          return;
        }
      }
    }
    // Couldn't move to the point, it may be off screen.
    return;
  }

  Vector3 lastMouseCoords = new Vector3(-1, -1, -1);
  Vector3 defaultCoords = new Vector3(100, 100, 0);

  public void mouseDragged(MouseEvent e) {
    if (e.getLocationOnScreen().x == defaultCoords.x && e.getLocationOnScreen().y == defaultCoords.y) {
      return;
    }

    if(lastMouseCoords.x == -1 && lastMouseCoords.y == -1){
      lastMouseCoords.x = e.getX();
    lastMouseCoords.y = e.getY();
      return;
    }

    // This code is for desktop
    //int xDiff = (int) (e.getLocationOnScreen().x - defaultCoords.x);
    //int yDiff = (int) (e.getLocationOnScreen().y - defaultCoords.y);
    //moveMouse(new Point(100, 100));

    // This code is for replit
    int xDiff = (int) (e.getX() - lastMouseCoords.x);
    int yDiff = (int) (e.getY() - lastMouseCoords.y);

    lastMouseCoords.x = e.getX();
    lastMouseCoords.y = e.getY();

    World.camera.rotation.y += xDiff / 1000.0 * World.camera.turnSensitivity;
    World.camera.rotation.x -= yDiff / 1000.0 * World.camera.turnSensitivity;

  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  public void mouseMoved(MouseEvent e) {

  }

  public void mousePressed(MouseEvent e) {
    
  }

  public void mouseEntered(MouseEvent e) {

  }

  public void mouseReleased(MouseEvent e) {
    lastMouseCoords.x = -1;
    lastMouseCoords.y = -1;
  }

  public void mouseClicked(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

  }
}