import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // title
        System.out.println("\033[34;43m\t\t\t\t\t\t  Pentago \033[0m\n");
        System.out.println("\033[31;44m\t\t\t\t\t\tBy M.Safari\033[0m\n");
        Thread.sleep(2000);
        // creating game board
        Board gBoard = new Board();
        // number of players
        int playerNum = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "\t\t\t\t\t\033[45mEnter 2 for two-player\033[0m\n\t\t\t\t\t\033[45mEnter 1 for one-player\033[0m");
        do {
            playerNum = sc.nextInt();
        } while (playerNum != 1 && playerNum != 2);
        // creating players
        Player[] gPlayer = new Player[2];
        {
            gPlayer[0] = new Player(House.RED);
            gPlayer[1] = ((playerNum == 1) ? new AI(House.BLACK, gBoard) : new Player(House.BLACK));
            gPlayer[0].setOpponent(gPlayer[1]);
            gPlayer[1].setOpponent(gPlayer[0]);
        }
        if (playerNum == 1) {
            System.out.println("One-Player Mode ...");
        } else {
            System.out.println("Two-Player Mode ...");

        }
        //
        boolean draw = false;
        // current player and marble
        Player player = gPlayer[0];
        House marble = player.marble;
        // initial board
        {
            gBoard.display(player, -1, -1);
        }
        while (true) {
            // trying to keep the game forward
            int i, j, r, d;

            if (player instanceof AI) {
                player.put();
            } else {
                System.out.println("Placing Marble");
                do {
                    i = sc.nextInt() - 1;
                    j = sc.nextInt() - 1;
                } while (!gBoard.putMarble(i, j, marble));
            }
            // board after placement
            {
                gBoard.display(player, i, j);
            }
            //
            if (gBoard.checkBoard(player)) {
                if (gBoard.checkBoard(player.getOpponent()))
                    draw = true;
                break;
            }
            //
            if (player instanceof AI) {
                player.rotate();
            } else {
                System.out.println("Rotating a Block");
                do {
                    System.out.println("1 | 2");
                    System.out.println("------");
                    System.out.println("4 | 3");
                    r = sc.nextInt() - 1;
                    System.out.println("in Which Direction:");
                    System.out.println(" 1 ) clockwise");
                    System.out.println("-1 ) counter-clockwise");
                    d = sc.nextInt();
                } while (!gBoard.rotateBlock(r, d));
                //
                if (gBoard.checkBoard(player)) {
                    if (gBoard.checkBoard(player.getOpponent()))
                        draw = true;
                    break;
                }
                //
            }
            // showing next player board design
            {
                player = player.getOpponent();
                marble = player.marble;
                gBoard.display(player, -1, -1);
            }
            Thread.sleep(500);
        }
        sc.close();
        // final result
        if (!draw)
            System.out.println("Player " + (player.marble == House.RED ? "Red" : "Black") + " Won!");
        else
            System.out.println("Game is draw");

    }
}