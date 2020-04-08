import java.util.*;

/**
 * house type enumerating the only three possible values for each house in game
 * board
 */
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

    /**
     * 
     * @return the opposite value if not empty
     */
    public House theOther() {
        if (this == BLACK)
            return RED;
        else if (this == RED)
            return BLACK;
        return EMPTY;
    }
}

/**
 * this class roles as base class of pentago game, containing board display and
 * marble placement, checking rules of game implementing
 * 
 * @author M.Safari
 * @version 1399.01.19
 */
public class Board {
    // stimulating the game board
    private House[][] board;
    // pointiong to center of blocks in board
    final private int[][] centers;

    /**
     * creating deault board
     */
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

    /**
     * displaying board with ⚪, 🔴, ⚫, chaaractars
     */
    public void display() {
        for (int j = 0; j < 6; j++)
            System.out.printf("%4s", (char) (j + 1));
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
                board[i - a][j - b] = board[i + b][j - a];
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

    public boolean isEmpty(int i, int j) {
        return (i >= 0 && j >= 0 && i < 6 && j < 6) ? (board[i][j] == House.EMPTY) : false;
    }

    public void putMarble(int i, int j, House marble) {
        if (isEmpty(i, j))
            board[i][j] = marble;
    }

    /**
     * counts a serie of same adjacent marbles
     * 
     * @param i
     * @param j
     * @param iStep
     * @param jStep
     * @param marble
     * @return
     */
    public int lineLength(int i, int j, int iStep, int jStep, House marble) {
        int count = 0;
        for (int a = i, b = j; ((a >= 0 && b >= 0 && a < 6 && b < 6)) && board[a][b] == marble; a += iStep, b += jStep)
            count++;
        return count;
    }

    /**
     * searching for a 5 same adjadcent marbles in a specific house in eight
     * directions
     * 
     * @param i
     * @param j
     * @param marble
     * @return
     */
    public boolean checkDirections(int i, int j, House marble) {
        for (int a = -1; a <= 1; a++)
            for (int b = -1; b <= 1; b++) {
                if (a == 0 && b == 0)
                    continue;
                if (lineLength(i, j, a, b, marble) >= 5)
                    return true;
            }
        return false;
    }

    /**
     * checking whole board for a five-adjacent set
     * 
     * @param player
     * @return
     */
    public boolean checkBoard(Player player) {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                if (checkDirections(i, j, player.marble)) {
                    return true;
                }
        return false;
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