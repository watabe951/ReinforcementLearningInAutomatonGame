public class Player extends GameObject {
    myCanvas canvas;
    int life;
    boolean muteki;

    Player(int initX, int initY, String upFilepath, String downFilepath, String rightFilepath, String leftFilepath) {
        super(initX, initY, upFilepath, downFilepath, rightFilepath, leftFilepath);
        life = 100;
        muteki = false;
    }

    @Override
    void setObject(Cell[][] cells) {
        // System.out.println("set at "+x+","+y);
        boolean kabe = false;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (cells[x + i][y + j].nextState == 5 && currentState[i][j] == 4) {
                    kabe = true;
                }
            }
        }
        if (!kabe) {
            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    if (cells[x + i][y + j].nextState == 0 || cells[x + i][y + j].nextState == 5
                            || cells[x + i][y + j].nextState == 4) {
                        cells[x + i][y + j].nextState = this.currentState[i][j];
                    } else if (cells[x + i][y + j].nextState == 6) {
                        cells[x + i][y + j].clearFlug = true;
                    } else {
                        cells[x + i][y + j].outFlug = true;
                    }

                }
            }
        }

    }

    @Override
    void move(int dx, int dy, Cellular cellular) {
        int newX;
        int newY;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                //System.out.println(cellular.cells[x + dx + i][y + dy + j].state);
                if (cellular.cells[x + dx + i][y + dy + j].state == 5) {
                    // System.out.println("kabe!");
                    dx = 0;
                    dy = 0;
                }
            }
        }
        newX = x + dx;
        newY = y + dy;
        if (0 <= newX && newX + this.xSize + 1 <= cellular.xSize && 0 <= newY && newY + this.ySize <= cellular.ySize) {
            x = newX;
            y = newY;
        }
    }
}
