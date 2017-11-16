import java.util.*;

public class Puzzle {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private char[][] defaultStartState = {
            {'W', 'W', 'W', 'W'},
            {'W', 'W', 'W', 'W'},
            {'W', 'W', 'W', 'W'},
            {'A', 'B', 'C', 'U'}};

    public static void main(String args[]){
        Puzzle p = new Puzzle();
        p.breadthFirst();
    }

    //TODO: Make a working visited Node HashSet that doesn't kill my CPU?
    private void breadthFirst(){

        LinkedList <Node> queue = new LinkedList <>();

        queue.add(new Node(null, defaultStartState));

        int searchCount = 1;
        boolean solutionFound = false;

        while(!queue.isEmpty() && !solutionFound) {
            Node current = queue.poll();

            if (isFinalState(current.getState())) {

                System.out.println("----SUCCESS----");
                solutionFound = true;

                while(current != null){
                    current.printState();
                    current = current.getParent();
                }
                System.out.println(searchCount);

            } else {
                char[][] tempState;

                ArrayList<Direction> moves = new ArrayList<>();
                moves.add(Direction.UP);
                moves.add(Direction.DOWN);
                moves.add(Direction.LEFT);
                moves.add(Direction.RIGHT);

                for (Direction d : moves) {
                    tempState = moveAgent(d, current.getState());
                    Node tempNode = new Node(current, tempState);
                    if(!isPrevVisited(tempNode))
                        if (!Arrays.deepEquals(tempState, current.getState()))
                            queue.add(tempNode);

                }
                searchCount++;
            }
        }
    }

    //TODO: Make a working visited Node HashSet that doesn't kill my CPU? Pretty sure isPrevVisited isn't strictly correct
    private void depthFirst(){

        Stack<Node> stack = new Stack<>();

        stack.push(new Node(null, defaultStartState));

        int searchCount = 1;
        boolean solutionFound = false;

        while(!stack.isEmpty() && !solutionFound) {
            Node current = stack.pop();

            if (!isPrevVisited(current))
                if (isFinalState(current.getState())) {

                    System.out.println("----SUCCESS----");
                    solutionFound = true;

                    while(current != null){
                        current.printState();
                        current = current.getParent();
                    }

                    System.out.println(searchCount);

                } else {
                    char[][] tempState;

                    ArrayList<Direction> moves = new ArrayList<>();
                    moves.add(Direction.UP);
                    moves.add(Direction.DOWN);
                    moves.add(Direction.LEFT);
                    moves.add(Direction.RIGHT);

                    for (Direction d : moves) {
                        tempState = moveAgent(d, current.getState());

                        if (!Arrays.deepEquals(tempState, current.getState()))
                            stack.push(new Node(current, tempState));

                    }

                    searchCount++;
                }
        }
    }

    private boolean isPrevVisited(Node n)
    {
        Node temp = n;

        while (temp.getParent() != null)
        {
            if (Arrays.deepEquals(n.getState(), temp.getParent().getState()))
                return true;

            temp = temp.getParent();
        }

        return false;
    }

    private boolean isFinalState(char[][] state)
    {
        int[] a = getBlockPosition('A', state);
        int[] b = getBlockPosition('B', state);
        int[] c = getBlockPosition('C', state);

        if(a[0] == b[0] && b[0] == c[0])
            if(a[1] == (b[1] - 1) && b[1] == (c[1] - 1))
                return true;

        return false;
    }

    private int[] getBlockPosition(Character blockName, char[][] state)
    {
        for(int y = 0; y < state.length; y++){
            for(int x = 0; x < state.length; x++) {
                if(state[y][x] == blockName)
                    return new int[]{x,y};
            }
        }
        return new int[]{-1,-1};
    }

    private char[][] moveAgent(Direction d, char[][] state){

        char[][] temp = new char[state.length][state.length];

        for(int y = 0; y < state.length; y++)
            for(int x = 0; x < state.length; x++)
                temp[y][x] = state[y][x];

        int[] agentPos = getBlockPosition('U', temp);
        int x = agentPos[0];
        int y = agentPos[1];

        switch (d) {
            case UP:
                if(y > 0)
                {
                    temp[y][x] = temp[y-1][x];
                    temp[y-1][x] = 'U';
                }
                break;
            case DOWN:
                if(y < temp.length - 1)
                {
                    temp[y][x] = temp[y+1][x];
                    temp[y+1][x] = 'U';
                }
                break;
            case LEFT:
                if(x > 0)
                {
                    temp[y][x] = temp[y][x-1];
                    temp[y][x-1] = 'U';
                }
                break;
            case RIGHT:
                if(x < temp.length - 1)
                {
                    temp[y][x] = temp[y][x+1];
                    temp[y][x+1] = 'U';
                }
                break;
            default:
                break;
        }
        return temp;
    }
}
