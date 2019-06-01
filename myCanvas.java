
/*
  myCanvas.java
*/
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class myCanvas extends Canvas implements Runnable {
  Thread th;
  Image buffer;
  Graphics bufferg;
  boolean waitFlag;
  Cellular cellular;
  Applet applet;
  int num_th;

  public myCanvas(Cellular cellular, Applet applet) {
    this.applet = applet;
    this.cellular = cellular;
    waitFlag = false;
    th = new Thread(this);
    num_th = 0;

    addKeyListener(new MyKeyListener(cellular));
  }

  public void start() {
    Dimension d = getSize();
    buffer = createImage((cellular.xSize) * 10, (cellular.ySize) * 10);
    th.start();

  }

  public void run() {
    try {
      while (true) {
        if (!this.waitFlag) {
          repaint();

          cellular.run();

        }
        th.sleep(5);
      }
    } catch (Exception e) {
    }
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paint(Graphics g) {
    if (bufferg == null)
      bufferg = buffer.getGraphics();
    // Dimension d = getSize();
    cellular.draw(bufferg, 10);

    // drawAxis(g);
    g.drawImage(buffer, 10, 10, this);

  }

  public class MyKeyListener implements KeyListener {
    Cellular cellular;

    MyKeyListener(Cellular cellular) {
      this.cellular = cellular;
    }

    @Override
    public void keyPressed(KeyEvent e) {
      cellular.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      cellular.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
      cellular.keyTyped(e);
    }
  }
}
