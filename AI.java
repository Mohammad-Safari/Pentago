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

    public void acrossBlocks(){}
    public void adjacentBlocks(){}

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
            if (origin.checkDirections(origin.centers[blocks.get(0)][0], origin.centers[blocks.get(0)][1], marble, 2)
                    || origin.checkDirections(origin.centers[blocks.get(1)][0], origin.centers[blocks.get(1)][1],
                            marble, 2)) // if no way between blocks has been shape , shape it
            {
                int[] steps = origin.whichDirections(origin.centers[blocks.get(0)][0], origin.centers[blocks.get(0)][1],
                        marble);
                // if can not put in such a house
                if (!origin.putMarble(steps[0], steps[1], marble)) {
                    steps = origin.whichDirections(origin.centers[blocks.get(1)][0], origin.centers[blocks.get(1)][1],
                            marble);
                    // if can not even in this house
                    if (!origin.putMarble(steps[0], steps[1], marble)) {
                        randPut();
                        return -1;
                    }
                }
            } else // if no way between blocks has been shape , shape it
            {
                int a, b, c = rand.nextInt(2), d = 0;
                do {
                    a = rand.nextInt(2) == 1 ? 1 : -1;
                    b = rand.nextInt(2) == 0 ? -1 : 1;
                    d++;
                } while (!fillBlock(blocks.get(c), a, b));
                if (d == 1000) {
                    randPut();
                    return -1;
                }
                return c;
            }

        } else // if two blocks are adjacent
        {
            if (origin.checkDirections(origin.centers[blocks.get(0)][0], origin.centers[blocks.get(0)][1], marble, 2)
                    || origin.checkDirections(origin.centers[blocks.get(1)][0], origin.centers[blocks.get(1)][1],
                            marble, 2)) // if there is a way we shape for penta-length
            {
                int[] steps = origin.whichDirections(origin.centers[blocks.get(0)][0], origin.centers[blocks.get(0)][1],
                        marble);
                if (!origin.putMarble(steps[0], steps[1], marble)) {
                    steps = origin.whichDirections(origin.centers[blocks.get(1)][0], origin.centers[blocks.get(1)][1],
                            marble);
                    if (!origin.putMarble(steps[0], steps[1], marble)) {
                        randPut();
                        return -1;
                    }
                    return -1;
                }
            } else // if no way between blocks has been shape , shape it
            {
                int a, b, c = rand.nextInt(2), d = 0;
                do {
                    a = rand.nextInt(3);
                    if (a == 0)
                        b = rand.nextInt(2) == 0 ? -1 : 1;
                    else
                        b = 0;
                    d++;
                } while (!fillBlock(blocks.get(c), a, b) || d == 1000);
                if (d == 1000) {
                    randPut();
                    return -1;
                }
                return c;
            }
        }
        return -1;
    }

    public void rotate(int block) {
        if (block == -1) {
            block = rand.nextInt(4);
        }
        if (!origin.checkDirections(origin.centers[block][0], origin.centers[block][1], marble, 3))
            origin.rotateBlock(block, 1);
        if (!origin.checkDirections(origin.centers[block][0], origin.centers[block][1], marble, 3)) {
            origin.rotateBlock(block, -1);
            origin.rotateBlock(block, -1);
        }
        if (!origin.checkDirections(origin.centers[block][0], origin.centers[block][1], marble, 3)) {
            origin.rotateBlock(block, 1);
            randRotate();
        }

    }

    public void randPut() {
        while (!origin.putMarble(rand.nextInt(6), rand.nextInt(6), marble))
            ;
    }

    public void randRotate() {
        origin.rotateBlock(rand.nextInt(4), ((rand.nextInt(2) == 1) ? 1 : -1));
    }

}