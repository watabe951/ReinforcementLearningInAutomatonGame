import java.io.*;

abstract class GameObject{
    int xSize;
    int ySize;
    int x;
    int y;
    String direction;
    int[][] currentState;
    int[][] upState;
    int[][] downState;
    int[][] rightState;
    int[][] leftState;
    myCanvas canvas;


    GameObject(int initX, int initY, String upFilepath, String downFilepath, String rightFilepath,String leftFilepath){
        currentState = new int[100][100];

        x = initX;
        y = initY;
        upState = new int[100][100];
        downState = new int[100][100];
        rightState = new int[100][100];
        leftState = new int[100][100];

        parseFile(upFilepath, upState);
        parseFile(downFilepath, downState);
        parseFile(rightFilepath, rightState);
        parseFile(leftFilepath, leftState);
    }
    void parseFile(String filepath,int[][] state){
        File file = new File(filepath);
        if(!file.exists()){
            System.out.print("ファイルが存在しません");
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
            System.out.println("parsing Object");
            while (( str = bufferedReader.readLine() ) != null ) {
                l++;
                if(str.charAt(0) == '#' || str.charAt(0) == '\n'){

                    continue;
                }

                xSize = str.length();

                for (int i = 0; i < str.length(); i++) {
                    state[i][lineNum] = Integer.parseInt(""+str.charAt(i));
                    System.out.print(Integer.parseInt(""+str.charAt(i)));
                }
                System.out.println("");
                lineNum++;
            }
            ySize = lineNum;

        } catch (FileNotFoundException e) {
            System.out.println("exception occured");
            System.out.println(e);

            return;
        }catch(IOException e ){
            System.out.println("exception occured");
            System.out.println(e);

            return;
        }

    }
    abstract void setObject(Cell[][] cells);
    abstract void move(int dx, int dy, Cellular cellular);

}
