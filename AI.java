import java.util.*;

public class AI extends Player {
    private Board origin;
    private ArrayList<Integer> blocks;
    Random rand = new Random();

    public AI(House marble, Board origin) {
        super(marble);
        this.origin = origin;
        blocks = new ArrayList<Integer>();
    }

    private boolean fillBlock(int block, int i, int j) {
        int[] centers = origin.centers[block];
        if (origin.putMarble(centers[0] + i, centers[1] + j, marble)) {
            if (!blocks.contains(block))
                blocks.add(block);
            return true;
        }
        return false;
    }

    private House getBlock(int block, int i, int j) {
        int[] centers = origin.centers[block];
        return origin.getHouse(centers[0] + i, centers[1] + j);

    }

    public int put() {
        // check blocks
        // manuvering upon two blocks
        if (blocks.size() < 2) {
            while (!fillBlock(rand.nextInt(4), 0, 0))
                ;
            return 0;
        }
        // if two blocks are across each other
        if (Math.abs(blocks.get(0) - blocks.get(1)) == 2) {

        } else // if two blocks are adjacent
        {
        }
        return -1;
    }

    public void rotate() {

    }

    public void randPut() {
        while (!origin.putMarble(rand.nextInt(6), rand.nextInt(6), marble))
            ;
    }

    public void randRotate() {
        origin.rotateBlock(rand.nextInt(4), ((rand.nextInt(2) == 1) ? 1 : -1));
    }

}