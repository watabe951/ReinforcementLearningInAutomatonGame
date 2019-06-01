import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
  <applet code = "mainApplet" width = 860 height = 860>
  </applet>
*/
public class mainApplet extends Applet implements ActionListener {
  myCanvas canvas;
  Agent agent;
  Cellular cellular;
  Button b[];

  public void init() {
    setLayout(null);
    setBackground(Color.white);
    agent = new Agent();
    cellular = new Cellular(this, agent);

    canvas = new myCanvas(cellular, this);
    cellular.init(canvas);
    canvas.setBounds(0, 0, cellular.xSize * 10, cellular.ySize * 10 + 1000);
    add(canvas);

    canvas.start();

    b = new Button[3];
    b[0] = new Button("start");
    b[1] = new Button("stop");
    b[2] = new Button("init");

    for (int i = 0; i < 3; i++) {
      b[i].addActionListener(this);
      b[i].setBounds(660 + i * 40, 120, 40, 20);
      b[i].addActionListener(this);
      add(b[i]);
    }
  }

  public void destroy() {
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == b[0]) {
      canvas.waitFlag = false;
    } else if (e.getSource() == b[1]) {
      canvas.waitFlag = true;
    } else if (e.getSource() == b[2]) {
      this.cellular.init(canvas);
    }
  }
}
