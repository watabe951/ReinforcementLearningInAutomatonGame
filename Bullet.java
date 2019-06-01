
public class Bullet extends GameObject {
    boolean birth = false;

    public Bullet(int initX, int initY, String upFilepath, String downFilepath, String rightFilepath,
            String leftFilepath) {
        super(initX, initY, upFilepath, downFilepath, rightFilepath, leftFilepath);
    }

    @Override
    void setObject(Cell[][] cells) {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                // System.out.println(this.currentState[i][j] );

                cells[x + i][y + j].nextState = this.currentState[i][j];

            }
        }
    }

    @Override
    void move(int dx, int dy, Cellular cellular) {

    }

    boolean birth(Player player, String str, Cellular cellular) {
        switch (str) {
        case "up":
            x = player.x + 1;
            y = player.y - 2 - this.ySize;
            break;
        case "down":
            x = player.x + 1;
            y = player.y + player.ySize + 1;
            break;
        case "right":
            x = player.x + this.xSize + 1;
            y = player.y + 1;
            break;
        case "left":
            x = player.x - this.xSize - 1;
            y = player.y + 1;
            break;

        default:
            break;
        }
        if (0 <= x && x + this.xSize + 1 <= cellular.xSize && 0 <= y && y + this.ySize <= cellular.ySize) {
            return true;
        } else {
            return false;
        }

    }

}
