public class Othello {

    private Board board;
    private static final int BOARDSIZE = 8;

    public static void main(String[] args) {
        Board board =  new Board(BOARDSIZE);
        Graphics g = new Graphics();
        g.initializeGraphics();
    }

}
