import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;

public class Cell {
    int x;
    int y;
    int state;
    int nextState;
    Boolean outFlug;
    Boolean clearFlug;
    Cell(int x, int y, int state){
        this.x = x;
        this.y = y;
        this.state = state;
        this.outFlug = false;
        this.clearFlug = false;
    }
    int getState(){
        return state;
    }
    void update(){
        this.state = this.nextState;
    }
    void setNextState(int[][] shuuhen){
        int sum = 0;
        outFlug = false;
        switch (this.state) {
            case 0:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (shuuhen[i][j] == 3) {
                            sum++;
                        }
                    }
                }
                if (sum == 2) {
                    this.nextState = 3;
                }
                break;
            case 1:
                this.nextState = 0;
                break;
            case 2:
                this.nextState = 1;
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (shuuhen[i][j] == 3) {
                            sum++;
                        }
                    }
                }
                if (3 <= sum && sum <= 5) {
                    this.nextState = 3;
                }else{
                    this.nextState = 2;
                }
                break;
            case 4:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (shuuhen[i][j] == 3) {
                            sum++;
                        }
                    }
                }
                if (sum == 2) {
                  outFlug = true;
                }
                this.nextState = 0;
                break;
            case 5:
                this.nextState = 5;
                break;
            case 6:
                this.nextState = 6;
            default:
                break;
        }
    }
    void draw(Graphics g, int cellSize){
        switch (this.state) {
            case 0:
                g.setColor(new Color(255, 255, 255));
                break;
            case 1:
                g.setColor(new Color(251, 200, 107));
                break;
            case 2:
                g.setColor(new Color(251, 126, 0));
                break;
            case 3:
                g.setColor(new Color(242, 0, 0));
                break;
            case 4:
                g.setColor(new Color(0, 0, 255));
                break;
            case 5:
                g.setColor(new Color(0, 255, 0));
                break;
            case 6:
                g.setColor(new Color(0, 125, 255));
                break;
            default:
                break;
        }
        g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
        // g.setColor(new Color(0, 0, 255));
        // g.drawString("aaa", (x+1)*cellSize, (y+1)*cellSize);
    }

}
