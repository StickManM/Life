import processing.core.PApplet;


public class Main extends PApplet {
    int[][] board;
    int columnCount, rowCount;
    int scale;

    public void settings() {
        size(1000,1000);
        pixelDensity(1);
    }

    public void setup() {
        scale = 10;
        rowCount = height/scale;
        columnCount = width/scale;
        board = new int [rowCount][columnCount];

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                board[r][c] = (int) random(2);
            }
        }
        noStroke();
    }

    public void draw() {
        background(0);
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                fill(255*board[r][c]);
                rect(c*scale,r*scale,scale,scale);
            }
        }
        update();
    }

    void update() {
        int[][] tempBoard = new int[rowCount][columnCount];
        int neighbors;
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                neighbors = countNeighbors(r,c);
                if (neighbors == 3) {
                    tempBoard[r][c] = 1;
                } else if (neighbors == 2) {
                    tempBoard[r][c] = board[r][c];
                } else {
                    tempBoard[r][c] = 0;
                }
            }
        }
        if (mousePressed) {
            int correspondingRow = (int) mouseY/scale;
            int correspondingColumn = (int) mouseX/scale;
            if(correspondingRow >= 0 && correspondingColumn >= 0 && correspondingRow < rowCount && correspondingColumn < columnCount){
                tempBoard[correspondingRow][correspondingColumn] = 1;
            }
        }

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                board[r][c] = tempBoard[r][c];
            }
        }
    }

    int countNeighbors(int r, int c) {
        int value = 0;
        int range = 1;
        int neighborR, neighborC;

        for (int dr = -range; dr <= range; dr++) {
            for (int dc = -range; dc <= range; dc++) {
                if (dr != 0|| dc != 0) {
                    neighborR = ((r + dr) + rowCount) % rowCount;
                    neighborC = ((c + dc) + columnCount) % columnCount;
                    value += board[neighborR][neighborC];

                }
            }
        }
        return value;
    }

    public static void main(String[] passedArgs) {
        PApplet.main(new String[] {Main.class.getName() });
    }
}