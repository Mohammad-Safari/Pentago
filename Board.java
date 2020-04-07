import java.util.*;

enum House {
    EMPTY, BLACK, RED;

    public String marble() {
        switch (this) {
            case BLACK:
                return "⚫";
            case RED:
                return "🔴";
            default:
                return "⚪";
        }
    }

    public House theOther() {
        if (this == BLACK)
            return RED;
        else if (this == RED)
            return BLACK;
        return EMPTY;
    }
}

class Board {
    private House[][] board;
    final private int[][] centers;

    public Board() {
        board = new House[6][];
        for (int i = 5; i >= 0; i--) {
            board[i] = new House[6];
            Arrays.fill(board[i], House.EMPTY);
        }
        centers = new int[4][];
        for (int i = 3; i >= 0; i--)
            centers[i] = new int[2];
        centers[0] = new int[] { 1, 1 };
        centers[1] = new int[] { 1, 4 };
        centers[2] = new int[] { 4, 4 };
        centers[3] = new int[] { 4, 1 };
    }

    public void display() {
        for (int j = 0; j < 6; j++)
            System.out.printf("%4s", (char)(j + 1));
        System.out.println();
        for (int i = 0; i < 6; i++) {
            if (i == 3) {
                for (int j = 0; j <= 6; j++) {
                    System.out.print("--");
                }
                System.out.println();
            }
            for (int j = 0; j < 6; j++) {
                if (j == 3)
                    System.out.print("|");
                System.out.print(board[i][j].marble());
            }
            System.out.println();
        }
    }

    public void rotateBlock(int region, int rotationDirection) {
        int i = centers[region][0], j = centers[region][1];
        for (int a = -1, b = -1; b < 1; b++) {
            House temp = board[a + i][b + j];
            if (rotationDirection == 1) {
                // rotating clockwise
                board[i + a][j + b] = board[i - b][j + a];
                board[i - b][j + a] = board[i - a][j - b];
                board[i - a][j - b] =  board[i + b][j - a];
                board[i + b][j - a] = temp;
            } else if (rotationDirection == -1) {
                // roatating counter-clockwise
                board[i + a][j + b] = board[i + b][j - a];
                board[i + b][j - a] = board[i - a][j - b];
                board[i - a][j - b] = board[i - b][j + a];
                board[i - b][j + a] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Board test = new Board();
        test.board[0][1] = House.BLACK;
        test.board[0][0] = House.RED;
        test.display();
        test.rotateBlock(0, 1);
        test.display();
        test.rotateBlock(0, 1);
        test.display();
    }

}