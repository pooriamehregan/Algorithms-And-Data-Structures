import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Read n, x, y
        int[] inputs = readNAndStartPos();
        // Create the board
        Board board = new Board(inputs[0]);
        board.findWay(inputs[1], inputs[2]);

    }

    /**
     * Reads input (n, x, and y) from user. It assumes the user types inn the correct number.
     * @return integer array containing [n, x, y]
     */
    private static int[] readNAndStartPos(){
        int[] inputs = new int[3];
        // Leser størrelsen på labyrinten og prosentandel blokkerte ruter
        Scanner scanner = new Scanner(System.in);
        System.out.print("n?: ");
        inputs[0] = scanner.nextInt();
        System.out.print("x?: ");
        inputs[1] = scanner.nextInt();
        System.out.print("y?: ");
        inputs[2] = scanner.nextInt();
        return inputs;
    }

}

class Board{
    private final int[][] B;
    private final int N, FREE = 0, MOVES = 8, START = 1;
    private final int[] xMoves = {2, 1, -1, -2, -2, -1, 1, 2};
    private final int[] yMoves = {1, 2, 2, 1, -1, -2, -2, -1};

    /**
     * Constructor that creates and initiates the board.
     * @param n is the x and y size of the board.
     */
    Board(int n){
        N = n;
        B = new int[n][n];
    }

    /**
     * Calls findWay which has a similar name but different signature, to find a solution to the Knight Tour problem.
     * If no solution is founded, it prints an appropriate message to the console.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void findWay(int x, int y){
        boolean found = findWay(START, x, y);
        if (!found) System.out.println("Knight did not visit all squares of the board!");
        else System.out.println(toString());
    }

    /**
     * Is a recursive method that calls itself to find a solution to the Knight Tour problem.
     * @param turn is the nr of the move that knight has taken.
     * @param x is the horizontal coordinate
     * @param y is the vertical coordinate
     * @return if all squares gets visited, it returns true, false otherwise.
     */
    private boolean findWay(int turn, int x, int y) {
        int nextX, nextY;
        if (turn == N * N + 1) {// means we have visited all squares
            return true;
        }

        for (int i = 0; i < MOVES; i++){
            nextX = x + xMoves[i];
            nextY = y + yMoves[i];

            if (hasNextMove(nextX, nextY)){
                B[nextX][nextY] = turn;

                if (findWay(turn+1, nextX, nextY)) {
                    return true;
                }

                B[nextX][nextY] = FREE; // init this square to unvisited
            }
        }
        return false;
    }

    /**
     * Checks if the next move of the knight is a possible to take. if not return false.
     * @param x next x coordinate
     * @param y next y coordinate
     * @return true if hasNextMove, false otherwise.
     */
    private boolean hasNextMove(int x, int y) {
        if (x < 0 ||    // x is out of bound
            x >= N ||   // ...
            y < 0 ||    // y is out of bound
            y >= N ||   // ...
            B[x][y] != 0) // square is visited
            return false;
        return true;
    }

    /**
     * returns the board as a n x n String square.
     * @return board as String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                    result.append(String.format("%2d",B[i][j])).append(" ");
            result.append("\n");
        }
        return result.toString();
    }
}
