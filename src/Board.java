import java.util.ArrayList;

public class Board {

    int boardsize;
    //The board itself. When checking for a space in board, check like this: [y-coordinate][x-coordinate].
    int[][] board;
    //IMPORTANT: currentColour is an int where 1 = black and 2 = white. In the board, 3 represents a spot where a piece with colour currentColour can be placed
    int currentColour;
    //Amount of total pieces on the board
    int pieceAmount;

    public Board(int boardsize) {
        if (boardsize % 2 == 0 && boardsize >= 4) {
            this.boardsize = boardsize;
            board = new int[boardsize][boardsize];
            setup();
            pieceAmount = 4;
            //black starts
            currentColour = 1;
        } else {
            System.out.println("Error!");
        }
    }

    public void move(int x, int y) {
        if(board[y][x] == 3) {
            board[y][x] = currentColour;
            flip(y, x);
            pront();
            pieceAmount++;
            if(currentColour == 1) {
                currentColour = 2;
            } else if (currentColour == 2) {
                currentColour = 1;
            } else {
                System.out.println("ERROR!");
            }
        } else {
            System.out.println("ERROR!");
        }
    }

    public boolean finished() {
        if (pieceAmount == boardsize*boardsize) {
            return true;
        }
        return false;
    }

    public void pront() {
        for(int i = 0; i < boardsize; i++) {
            for(int j = 0; j < boardsize; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setup() {
        int half = boardsize/2;
        board[half - 1][half - 1] = 1;
        board[half][half - 1] = 2;
        board[half - 1][half] = 2;
        board[half][half] = 1;
    }

    /**
     * method that checks the neighbor (empty) spots of each opponent tile placed on the board. While checking each opponent tile's neighbors,
     * when an empty one is found, method checks whether placing a tile there will make a legal by moving in the direction to the opponent tile being checked
     * and checking whether there exists a line of opponent tiles ending in a tile of the current color to close the move. If so, the empty neighbor spot around the opponent
     * checked spot will be marked with a 3, which represents a possible spot to make a move. This process is done for every possible empty cell surrounding every
     * opponent tile set on the board.
     *
     */
    public void checkMoves() {
        delete3s();
        int opponentColor;
        if (currentColour == 1) {
            opponentColor = 2;
        } else {
            opponentColor = 1;
        }
        ArrayList<int[]> opponentSpots = new ArrayList<>();
        opponentSpots = findOpponentSpots(opponentColor);
        int counter = 0;
        while (counter < opponentSpots.size()) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int xCoor = opponentSpots.get(counter)[0];
                    int yCoor = opponentSpots.get(counter)[1];
                    if (board[xCoor + i][yCoor + j] == 0) {
                        int temp_i = -1 * i;
                        int temp_j = -1 * j;
                        while (((xCoor + temp_i) >= 0) && ((xCoor + temp_i) < board.length) && ((yCoor + temp_j) >= 0) && ((yCoor + temp_j) < board[0].length) && (board[xCoor + temp_i][yCoor + temp_j] == opponentColor)) {
                            xCoor += temp_i;
                            yCoor += temp_j;
                        }
                        if (board[xCoor + temp_i][yCoor + temp_j] == currentColour) {
                            board[opponentSpots.get(counter)[0]+i][opponentSpots.get(counter)[1]+j] = 3;
                        }
                    }
                }
            }
            counter++;
        }
    }

    /**
     * This method goes through the board and finds the coordinates of the opponent tiles already set on the board.
     * Then all these coordinates are saved into arrays, which are saved at the same time within an arrayList.
     * @param opponentColor to represent the opponent color to the variable "color"
     * @return arrayList containing arrays with coordinates x and y of each opponent tile on the board
     */

    public ArrayList<int[]> findOpponentSpots(int opponentColor){
        ArrayList<int[]> opponentSpots = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == opponentColor){
                    int[] position = new int[2];
                    position[0] = i; //x
                    position[1] = j; //y
                    opponentSpots.add(position);
                }
            }
        }
        return opponentSpots;
    }

   public void delete3s(){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[0].length; j++){
                if(board[i][j] == 3){
                    board[i][j] = 0;
                }
            }
        }
   }

    /**
     * This method is initialized after a piece was placed. It flips the respective pieces of the opponent.
     **/
    public void flip ( int x, int y){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && i == j)) {
                    int amount = checkDirection(x, y, i, j);
                    if (amount != 0) {
                        int currentX = x + i;
                        int currentY = y + j;
                        for (int k = 0; k < amount; k++) {
                            board[currentX][currentY] = currentColour;
                            currentX += i;
                            currentY += j;
                        }
                    }
                }
            }
        }
    }

    /**
     * @return amount of chips being flipped in one direction. If amount is 0, no chips can be flipped.
     * x and y refer to the coordinates in the grid.
     * xDirection and yDirection show the x-and y-Distance and Direction that is traveled when checking. They should only be set to 1, 0 or -1.
     * <p>
     * As an example: If both are set to -1, the direction that is checked is up left
     * If xDirection is set to 0, and yDirection is set to 1, the direction that is checked is straight down.
     **/
    public int checkDirection ( int x, int y, int xDirection, int yDirection){
        int currentX = x + xDirection;
        int currentY = y + yDirection;
        int amount = 0;
        int opposingColour;

        if (currentColour == 1) {
            opposingColour = 2;
        } else if (currentColour == 2) {
            opposingColour = 1;
        } else {
            System.out.println("ERROR!");
            return 0;
        }

        while (currentX >= 0 && currentX <= board.length && currentY >= 0 && currentY <= board.length && board[currentY][currentX] == opposingColour) {
            amount++;
            currentX += xDirection;
            currentY += yDirection;
        }
        return amount;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentColour() {
        return currentColour;
    }

}
