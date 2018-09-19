import java.util.Scanner;

public class Othello {

    private Board board;
    private static final int BOARDSIZE = 8;

    public static void main(String[] args) {
        Board board =  new Board(BOARDSIZE);
        board.pront();

        Scanner in = new Scanner(System.in);

        while (!board.finished()) {
            board.checkMoves();
            board.pront();
            int x = in.nextInt();
            int y = in.nextInt();
            board.move(x, y);
        }
        //Graphics g = new Graphics();
        //g.initializeGraphics();
    }

}
