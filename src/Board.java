import java.util.Scanner;

public class Board {

        private int[][] board;
        private static int white_checkers; //white is 2
        private static int black_checkers; //black is 1
        private static int turn; //even for white, odd for black

        public Board(int boardSize) {
            white_checkers = (boardSize*2)/2;
            black_checkers = (boardSize*2)/2;
            board = new int[boardSize][boardSize];
            setUp();
            printBoard();
            //checkPossibleSpaces();
            checkMoves();
        }

        public void updateCheckers(){
        }

        public void setUp(){
            //set top row
            int position = board.length / 2;
            board[position-1][position-1] = 1;
            addedBlack();
            board[position-1][position] = 2;
            addedWhite();
            //set bottom row
            board[position][position-1] = 2;
            addedWhite();
            board[position][position] = 1;
            addedBlack();
        }

        public void printBoard(){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
        }

        public void checkMoves(){
            Scanner in = new Scanner(System.in);
            System.out.println("Input coordinates of your move");
            System.out.println("x = ");
            int tempX = in.nextInt();
            System.out.println("y = ");
            int tempY = in.nextInt();
            in.close();
        }

        public void addedBlack(){ //every time a piece is used, this method gets called
            black_checkers--;
        }

        public void addedWhite(){ //every time a piece is used, this method gets called
            white_checkers--;
        }
    }

