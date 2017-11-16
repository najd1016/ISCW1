public class Node {

    private Node parent;
    private char[][] state;

    public Node(Node parent, char[][] state){
        this.parent = parent;
        this.state = state;
    }

    public char[][] getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public void printState(){
        for(int y = 0; y < state.length; y++){
            for(int x = 0; x < state.length; x++){
                System.out.print(state[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
