import java.io.*;
import java.util.Objects;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cellular {

    Cell[][] cells;
    int[] coordinate;

    int xSize;
    int ySize;
    int cellSize = 10;
    int mutekiInc;
    Player player;
    Bullet bullet;
    myCanvas canvas;
    mainApplet applet;
    int[][] stage;
    File file;
    boolean outFlug;
    boolean gameOver;
    boolean clearFlug;
    Agent agent;
    int step = 0;
    boolean running;

    Cellular(mainApplet applet, Agent agent) {
        this.applet = applet;
        this.agent = agent;
        running = false;
    }

    void parseFile() {
        file = new File("./stage05.txt");
        if (!file.exists()) {
            // System.out.print("ファイルが存在しません");
            return;
        }
        FileReader fileReader;
        BufferedReader bufferedReader;
        String str;
        int lineNum = 0;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            int l = 0;
            while ((str = bufferedReader.readLine()) != null) {
                l++;
                if (str.charAt(0) == '#' || str.charAt(0) == '\n') {

                    continue;
                }
                if (str.contains("start")) {

                    String[] splited = str.replaceAll("[a-zA-Z]+: | +", "").split(",");
                    for (int i = 0; i < splited.length; i++) {
                        coordinate[i] = Integer.parseInt(splited[i]);
                    }
                } else {
                    String s = str.replaceAll(" +# +[0-9]+", "");

                    xSize = s.length();

                    for (int i = 0; i < s.length(); i++) {
                        stage[lineNum][i] = Integer.parseInt("" + s.charAt(i));
                    }
                    lineNum++;
                }
                ySize = lineNum;
            }
            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    cells[i][j].state = stage[j][i];
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("exception occured");
            System.out.println(e);

            return;
        } catch (IOException e) {
            System.out.println("exception occured");
            System.out.println(e);

            return;
        }

    }

    void init(myCanvas canvas) {
        while (running) {
        }
        this.canvas = canvas;
        this.stage = new int[1000][1000];

        this.gameOver = false;
        this.clearFlug = false;
        coordinate = new int[2];

        this.canvas.waitFlag = true;
        this.cells = new Cell[1000][1000];
        this.gameOver = false;
        this.clearFlug = false;

        coordinate = new int[2];
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                this.stage[i][j] = 0;
            }
        }
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 100; j++) {
                this.cells[i][j] = new Cell(i, j, 0);
            }
        }

        parseFile();
        player = new Player(coordinate[0], coordinate[1], "./playerUP.txt", "./playerDOWN.txt", "./playerRIGHT.txt",
                "./playerLEFT.txt");
        bullet = new Bullet(0, 0, "./bulletUP.txt", "./bulletDOWN.txt", "./bulletRIGHT.txt", "./bulletLEFT.txt");
        agent.init(this.player, this);
        this.canvas.waitFlag = false;
    }

    int[][] getShuuhen(int x, int y) {
        int[][] shuuhen = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (0 <= (x - 1 + i) && (x - 1 + i) <= xSize && 0 <= (y - 1 + j) && (y - 1 + j) <= ySize) {
                    shuuhen[i][j] = cells[x - 1 + i][y - 1 + j].state;
                } else {

                    shuuhen[i][j] = 0;
                }
            }
        }
        shuuhen[1][1] = 0;// Rule:Center is 0
        return shuuhen;
    }

    void run() {
        running = true;
        this.step++;
        agent.Observe();
        // for (int i = 0; i < this.xSize; i++) {
        // for (int j = 0; j < this.ySize; j++) {
        // System.out.print(cells[i][j].state);
        // }
        // }
        switch (this.agent.policy()) {
        case 0:
            player.move(0, -1, this);
            player.currentState = player.upState;
            bullet.currentState = bullet.upState;
            break;
        case 1:
            player.move(0, 1, this);
            player.currentState = player.downState;
            bullet.currentState = bullet.downState;
            break;
        case 2:
            player.move(1, 0, this);
            player.currentState = player.rightState;
            bullet.currentState = bullet.rightState;
            break;
        case 3:
            player.move(-1, 0, this);
            player.currentState = player.leftState;
            bullet.currentState = bullet.leftState;
            break;
        case 4:
            if (player.currentState == player.upState) {
                bullet.birth = bullet.birth(player, "up", this);
            } else if (player.currentState == player.downState) {
                bullet.birth = bullet.birth(player, "down", this);
            } else if (player.currentState == player.rightState) {
                bullet.birth = bullet.birth(player, "right", this);
            } else if (player.currentState == player.leftState) {
                bullet.birth = bullet.birth(player, "left", this);
            }

        default:
            break;
        }
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                int[][] shuuhen = new int[3][3];
                shuuhen = getShuuhen(j, i);
                cells[j][i].setNextState(shuuhen);
            }
        }
        // for (int i = 0; i < this.xSize; i++) {
        // for (int j = 0; j < this.ySize; j++) {
        // System.out.print(cells[i][j].state);
        // }
        // System.out.println();
        // }
        player.setObject(cells);
        if (bullet.birth) {

            bullet.setObject(cells);
            bullet.birth = false;
        }
        for (int i = 0; i < this.xSize; i++) {
            for (int j = 0; j < this.ySize; j++) {
                if (cells[i][j].state == 5) {
                    cells[i][j].nextState = 5;
                }
            }
        }
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                cells[i][j].update();
                if (cells[i][j].outFlug & !player.muteki) {
                    player.life--;
                    player.muteki = true;
                    mutekiInc = 0;
                }

                if (cells[i][j].clearFlug) {
                    clearFlug = true;
                }
            }
        }
        if (player.life <= 0) {
            running = false;
            this.init(canvas);
        }
        // for (int i = 0; i < this.xSize; i++) {
        // for (int j = 0; j < this.ySize; j++) {
        // System.out.print(cells[i][j].state);
        // }
        // System.out.println();
        // }
        if (player.muteki) {
            mutekiInc++;
            if (mutekiInc > 20) {
                player.muteki = false;
            }
        }
        running = false;
    }

    void draw(Graphics g, int cellSize) {

        if (gameOver) {

            g.setColor(new Color(0, 0, 0));
            g.drawString("GameOver", 20, 20);

        } else if (clearFlug) {
            g.setColor(new Color(0, 0, 0));
            g.drawString("GameClear", 20, 20);
        } else {
            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    this.cells[i][j].draw(g, cellSize);
                }
            }
            g.setColor(new Color(0, 0, 0));
            g.drawString("life:" + player.life, 20, 50);
        }

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

}
